package com.opencore.asciidoctor;

import com.mxgraph.io.mxCodec;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import org.asciidoctor.ast.AbstractBlock;
import org.asciidoctor.extension.BlockMacroProcessor;
import org.w3c.dom.Document;

public class DrawioMacro extends BlockMacroProcessor {
  private mxGraph graph = new mxGraph();

  public DrawioMacro(String macroName) {
    super(macroName);
  }

  @Override
  protected Object process(AbstractBlock abstractBlock, String s, Map<String, Object> map) {
    Document doc = mxXmlUtils.parseXml(s);
    mxCodec codec = new mxCodec(doc);
    codec.decode(doc.getDocumentElement(), graph.getModel());
    RenderedImage image = mxCellRenderer.createBufferedImage(graph, null, 1,
        Color.WHITE, false, null);

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try {
      ImageIO.write(image, "png", bos);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return bos.toString();
  }
}
