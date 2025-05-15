package modelo;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.chart.JFreeChart;

import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.awt.Color;
import java.sql.*;
import java.io.IOException;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

public class PDFExportador
{

    public static void exportChartsToPDF(List<JFreeChart> charts, String filePath) throws IOException
    {
        // Validación de parámetros
        if (charts == null)
        {
            throw new IllegalArgumentException("La lista de gráficos no puede ser null");
        }
        if (charts.isEmpty())
        {
            throw new IllegalArgumentException("La lista de gráficos está vacía");
        }
        if (filePath == null || filePath.trim().isEmpty())
        {
            throw new IllegalArgumentException("La ruta del archivo no puede estar vacía");
        }

        try (PDDocument document = new PDDocument())
        {
            PDRectangle pageSize = new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth());
            float margin = 50;

            for (JFreeChart chart : charts)
            {
                if (chart == null)
                {
                    System.err.println("Advertencia: Se encontró un gráfico null en la lista");
                    continue;
                }

                PDPage page = new PDPage(pageSize);
                document.addPage(page);

                float availableWidth = pageSize.getWidth() - (2 * margin);
                float availableHeight = pageSize.getHeight() - (2 * margin);

                BufferedImage chartImage = createChartImage(chart, (int) availableWidth, (int) availableHeight);
                PDImageXObject pdfImage = LosslessFactory.createFromImage(document, chartImage);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page))
                {
                    contentStream.drawImage(pdfImage, margin, margin, availableWidth, availableHeight);
                }
            }

            document.save(new File(filePath));
        }
    }

    private static BufferedImage createChartImage(JFreeChart chart, int width, int height)
    {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        chart.draw(g2, new Rectangle2D.Double(0, 0, width, height));
        g2.dispose();
        return image;
    }

    public static void exportVentasToPDF(Connection connection, String filePath)
            throws IOException, SQLException
    {

        // Usar fuentes estándar que siempre existen
        PDType1Font headerFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
        PDType1Font bodyFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

        try (PDDocument document = new PDDocument())
        {
            // 1. Exportar Métodos de Pago
            exportTable(document, connection,
                    "SELECT * FROM Metodo_Pago",
                    "Métodos de Pago",
                    headerFont, bodyFont);

            // 2. Exportar Ventas con JOINs
            String ventasQuery = "SELECT v.id_venta, ve.nombre AS vendedor, c.nombre AS cliente, "
                    + "m.tipo_pago, v.fecha_compra, v.total "
                    + "FROM Ventas v "
                    + "JOIN Vendedor ve ON v.id_vendedor = ve.id_vendedor "
                    + "JOIN Cliente c ON v.id_cliente = c.id_cliente "
                    + "JOIN Metodo_Pago m ON v.id_pago = m.id_pago";
            exportTable(document, connection, ventasQuery, "Ventas", headerFont, bodyFont);

            // 3. Exportar Detalles de Ventas
            exportTable(document, connection,
                    "SELECT * FROM Detalle_Venta",
                    "Detalles de Ventas",
                    headerFont, bodyFont);

            document.save(filePath);
        }
    }

    private static void exportTable(PDDocument document, Connection connection,
            String query, String title,
            PDType1Font headerFont, PDType1Font bodyFont)
            throws SQLException, IOException
    {

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query))
        {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] headers = new String[columnCount];

            for (int i = 1; i <= columnCount; i++)
            {
                headers[i - 1] = metaData.getColumnName(i);
            }

            // Crear página en orientación horizontal (sin usar rotate())
            PDPage page = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
            document.addPage(page);

            PDPageContentStream contentStream = null;
            try
            {
                contentStream = new PDPageContentStream(document, page);

                // Configuración inicial
                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float yPosition = yStart;
                int fontSize = 10;

                // Título de la tabla
                contentStream.setFont(headerFont, fontSize + 4);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText(title);
                contentStream.endText();
                yPosition -= 30;

                // Calcular ancho de columnas
                float[] columnWidths = new float[columnCount];
                float columnWidth = tableWidth / columnCount;
                for (int i = 0; i < columnCount; i++)
                {
                    columnWidths[i] = columnWidth;
                }

                // Dibujar cabecera
                drawHeader(contentStream, margin, yPosition, columnWidths, headers, headerFont, fontSize);
                yPosition -= 25;

                // Dibujar filas
                int rowCount = 0;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                while (rs.next())
                {
                    // Nueva página cada 25 filas
                    if (rowCount > 0 && rowCount % 25 == 0)
                    {
                        contentStream.close();
                        page = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        yPosition = yStart - 30;
                        drawHeader(contentStream, margin, yPosition, columnWidths, headers, headerFont, fontSize);
                        yPosition -= 25;
                    }

                    // Dibujar fila
                    drawRow(contentStream, margin, yPosition, columnWidths, rs, columnCount, bodyFont, fontSize, dateFormat);
                    yPosition -= 20;
                    rowCount++;
                }

            } finally
            {
                if (contentStream != null)
                {
                    contentStream.close();
                }
            }
        }
    }

    private static void drawHeader(PDPageContentStream contentStream, float x, float y,
            float[] columnWidths, String[] headers,
            PDType1Font font, int fontSize) throws IOException
    {
        contentStream.setFont(font, fontSize);
        contentStream.setNonStrokingColor(new Color(70, 130, 180)); // Azul acero

        for (int i = 0; i < headers.length; i++)
        {
            drawCell(contentStream, x, y, columnWidths[i], 20, headers[i]);
            x += columnWidths[i];
        }
    }

    private static void drawRow(PDPageContentStream contentStream, float x, float y,
            float[] columnWidths, ResultSet rs, int columnCount,
            PDType1Font font, int fontSize, SimpleDateFormat dateFormat)
            throws SQLException, IOException
    {

        contentStream.setFont(font, fontSize);
        contentStream.setNonStrokingColor(Color.BLACK);

        for (int i = 1; i <= columnCount; i++)
        {
            Object value = rs.getObject(i);
            String text = formatValue(value, dateFormat);
            drawCell(contentStream, x, y, columnWidths[i - 1], 20, text);
            x += columnWidths[i - 1];
        }
    }

    private static String formatValue(Object value, SimpleDateFormat dateFormat)
    {
        if (value == null)
        {
            return "NULL";
        }
        if (value instanceof java.util.Date)
        {
            return dateFormat.format(value);
        }
        if (value instanceof Double || value instanceof Float)
        {
            return String.format("$%,.2f", value);
        }
        return value.toString();
    }

    private static void drawCell(PDPageContentStream contentStream, float x, float y,
            float width, float height, String text) throws IOException
    {
        // Fondo de celda
        contentStream.setNonStrokingColor(new Color(240, 240, 240));
        contentStream.addRect(x, y, width, height);
        contentStream.fill();

        // Borde de celda
        contentStream.setNonStrokingColor(Color.LIGHT_GRAY);
        contentStream.addRect(x, y, width, height);
        contentStream.stroke();

        // Texto (truncado si es muy largo)
        String displayText = text.length() > 25 ? text.substring(0, 22) + "..." : text;
        contentStream.beginText();
        contentStream.newLineAtOffset(x + 5, y + height - 15);
        contentStream.showText(displayText);
        contentStream.endText();
    }
}
