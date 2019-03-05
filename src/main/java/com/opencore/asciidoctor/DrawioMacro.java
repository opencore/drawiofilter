package com.opencore.asciidoctor;

import com.mxgraph.io.mxCodec;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.asciidoctor.ast.AbstractBlock;
import org.asciidoctor.extension.BlockProcessor;
import org.asciidoctor.extension.Reader;
import org.w3c.dom.Document;

public class DrawioMacro extends BlockProcessor {
  private mxGraph graph = new mxGraph();

  public DrawioMacro(String name, Map<String, Object> config) {
    super(name, createConfig());
  }

  @Override
  public Object process(AbstractBlock abstractBlock, Reader reader, Map<String, Object> map) {
    StringBuilder blockContent = new StringBuilder();
    List<String> lines = reader.readLines();
    for (String line : lines) {
      blockContent.append(line);
    }

    Document doc = mxXmlUtils.parseXml(blockContent.toString());
    mxCodec codec = new mxCodec(doc);
    codec.decode(doc.getDocumentElement(), graph.getModel());
    RenderedImage image = mxCellRenderer.createBufferedImage(graph, null, 1,
        Color.WHITE, false, null);

    String path = "target/generated-slides/";
    String fileName = "images/" + map.get(2).toString() + ".png";
    try {
      ImageIO.write(image, "png", new File(path + fileName));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return createBlock(abstractBlock, "literal","<img src=\"" + fileName + "\" alt=\"asciidoctor diagram process\" />",map, new HashMap<Object, Object>());
  }

  private static Map<String, Object> createConfig() {
    Map<String, Object> result = new HashMap<>();
    result.put("contexts", createContextsConfig());
    return result;
  }

  private static List<String> createContextsConfig() {
    List<String> contexts = new ArrayList<>();
    contexts.add(":literal");
    return contexts;
  }
}
