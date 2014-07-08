/*
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * $Id: ElemTextLiteral.java,v 1.19 2005/01/23 00:27:29 mcnamara Exp $
 */
package org.apache.xalan.templates;

import javax.xml.transform.TransformerException;

import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.serializer.SerializationHandler;
import org.xml.sax.SAXException;

/**
 * Implement a text literal.
 * @see <a href="http://www.w3.org/TR/xslt#section-Creating-Text">section-Creating-Text in XSLT Specification</a>
 * @xsl.usage advanced
 */
public class ElemTextLiteral extends ElemTemplateElement
{
    static final long serialVersionUID = -7872620006767660088L;

  /**
   * Tell if space should be preserved.
   * @serial
   */
  private boolean m_preserveSpace;

  /**
   * Set whether or not space should be preserved.
   *
   * @param v Boolean flag indicating whether 
   * or not space should be preserved
   */
  public void setPreserveSpace(boolean v)
  {
    m_preserveSpace = v;
  }

  /**
   * Get whether or not space should be preserved.
   *
   * @return Boolean flag indicating whether 
   * or not space should be preserved 
   */
  public boolean getPreserveSpace()
  {
    return m_preserveSpace;
  }

  /**
   * The character array.
   * @serial
   */
  private char m_ch[];
  
  /**
   * The character array as a string.
   * @serial
   */
  private String m_str;

  /**
   * Set the characters that will be output to the result tree..
   *
   * @param v Array of characters that will be output to the result tree 
   */
  public void setChars(char[] v)
  {
    m_ch = v;
  }

  /**
   * Get the characters that will be output to the result tree..
   *
   * @return Array of characters that will be output to the result tree
   */
  public char[] getChars()
  {
    return m_ch;
  }
  
  /**
   * Get the value of the node as a string.
   *
   * @return null
   */
  public synchronized String getNodeValue()
  {

    if(null == m_str)
    {
      m_str = new String(m_ch);
    }

    return m_str;
  }


  /**
   * Tells if this element should disable escaping.
   * @serial
   */
  private boolean m_disableOutputEscaping = false;

  /**
   * Set the "disable-output-escaping" attribute.
   * Normally, the xml output method escapes & and < (and
   * possibly other characters) when outputting text nodes.
   * This ensures that the output is well-formed XML. However,
   * it is sometimes convenient to be able to produce output
   * that is almost, but not quite well-formed XML; for
   * example, the output may include ill-formed sections
   * which are intended to be transformed into well-formed
   * XML by a subsequent non-XML aware process. For this reason,
   * XSLT provides a mechanism for disabling output escaping.
   * An xsl:value-of or xsl:text element may have a
   * disable-output-escaping attribute; the allowed values
   * are yes or no; the default is no; if the value is yes,
   * then a text node generated by instantiating the xsl:value-of
   * or xsl:text element should be output without any escaping.
   * @see <a href="http://www.w3.org/TR/xslt#disable-output-escaping">disable-output-escaping in XSLT Specification</a>
   *
   * @param v Boolean value for "disable-output-escaping" attribute.
   */
  public void setDisableOutputEscaping(boolean v)
  {
    m_disableOutputEscaping = v;
  }

  /**
   * Get the "disable-output-escaping" attribute.
   * Normally, the xml output method escapes & and < (and
   * possibly other characters) when outputting text nodes.
   * This ensures that the output is well-formed XML. However,
   * it is sometimes convenient to be able to produce output
   * that is almost, but not quite well-formed XML; for
   * example, the output may include ill-formed sections
   * which are intended to be transformed into well-formed
   * XML by a subsequent non-XML aware process. For this reason,
   * XSLT provides a mechanism for disabling output escaping.
   * An xsl:value-of or xsl:text element may have a
   * disable-output-escaping attribute; the allowed values
   * are yes or no; the default is no; if the value is yes,
   * then a text node generated by instantiating the xsl:value-of
   * or xsl:text element should be output without any escaping.
   * @see <a href="http://www.w3.org/TR/xslt#disable-output-escaping">disable-output-escaping in XSLT Specification</a>
   *
   * @return Boolean value of "disable-output-escaping" attribute.
   */
  public boolean getDisableOutputEscaping()
  {
    return m_disableOutputEscaping;
  }

  /**
   * Get an integer representation of the element type.
   *
   * @return An integer representation of the element, defined in the
   *     Constants class.
   * @see Constants
   */
  public int getXSLToken()
  {
    return Constants.ELEMNAME_TEXTLITERALRESULT;
  }

  /**
   * Return the node name.
   *
   * @return The element's name
   */
  public String getNodeName()
  {
    return "#Text";
  }

  /**
   * Copy the text literal to the result tree.
   *
   * @param transformer non-null reference to the the current transform-time state.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public void execute(
          TransformerImpl transformer)
            throws TransformerException
  {
    try
    {
      SerializationHandler rth = transformer.getResultTreeHandler();
      if (transformer.getDebug()) {
        // flush any pending cached processing before the trace event.
        rth.flushPending();
        transformer.getTraceManager().fireTraceEvent(this);
      }

      if (m_disableOutputEscaping)
      {
        rth.processingInstruction(javax.xml.transform.Result.PI_DISABLE_OUTPUT_ESCAPING, "");
      }

      rth.characters(m_ch, 0, m_ch.length);

      if (m_disableOutputEscaping)
      {
        rth.processingInstruction(javax.xml.transform.Result.PI_ENABLE_OUTPUT_ESCAPING, "");
      }
    }
    catch(SAXException se)
    {
      throw new TransformerException(se);
    }
    finally
    {
      if (transformer.getDebug()) {
        try
        {
            // flush any pending cached processing before sending the trace event
            transformer.getResultTreeHandler().flushPending();
            transformer.getTraceManager().fireTraceEndEvent(this);
        }
        catch (SAXException se)
        {
            throw new TransformerException(se);
        } 
      }
    }
  }
}
