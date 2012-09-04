  <xsl:stylesheet
     xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
     xmlns:xsd="http://www.w3.org/2001/XMLSchema"
     version="1.0">

<xsl:template match="xsd:annotation"/>

     <xsl:template match="@* | node()">
       <xsl:copy>
         <xsl:apply-templates select="@* | node()"/>
       </xsl:copy>
     </xsl:template>

</xsl:stylesheet>
