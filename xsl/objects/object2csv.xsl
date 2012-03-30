<?xml version= "1.0" encoding= "UTF-8" ?>
<xsl:stylesheet
    version         = "1.0"
    xmlns:xsl       = "http://www.w3.org/1999/XSL/Transform"
    xmlns:xs        = "http://www.w3.org/2001/XMLSchema"
    xmlns:fn        = "http://www.w3.org/2005/xpath-functions"

	xmlns:sfdc      = "http://soap.sforce.com/2006/04/metadata"

    exclude-result-prefixes= "xsl xs fn">

    <!-- ============================================================================================ -->

    <xsl:output method="text" version="1.0" indent="yes"/>

    <!-- ============================================================================================ -->

    <xsl:template match="* | @* | text()"/>

    <!-- ============================================================================================ -->

    <xsl:template match="/">"field_name","type","size"
<xsl:apply-templates mode="emitCsv" select="sfdc:CustomObject/sfdc:fields">
	<xsl:sort select="sfdc:fullName"/>
</xsl:apply-templates>
    </xsl:template>

    <!-- ============================================================================================ -->

	<xsl:template mode="emitCsv" match="sfdc:fields">
		<xsl:variable name="vFULL_NAME">
			<xsl:apply-templates mode="nameValue" select="sfdc:fullName"/>
		</xsl:variable>

		<xsl:variable name="vTYPE_VALUE">
			<xsl:apply-templates mode="typeValue" select="."/>
		</xsl:variable>

		<xsl:variable name="vLENGTH_VALUE">
			<xsl:apply-templates mode="lengthValue" select="."/>
		</xsl:variable>
<xsl:value-of select="normalize-space($vFULL_NAME)"/>,<xsl:value-of select="normalize-space($vTYPE_VALUE)"/>,<xsl:value-of select="normalize-space($vLENGTH_VALUE)"/>
<xsl:text>
</xsl:text>
	</xsl:template>

    <!-- ============================================================================================ -->

	<xsl:template mode="nameValue" match="sfdc:fullName">
		"<xsl:value-of select="normalize-space ( . )"/>"
	</xsl:template>

    <!-- ============================================================================================ -->

	<xsl:template mode="typeValue" match="sfdc:fields [ sfdc:type ]">
		"<xsl:value-of select="normalize-space ( sfdc:type )"/>"
	</xsl:template>

	<xsl:template mode="typeValue" match="sfdc:fields [ sfdc:picklist ]">
		"<xsl:value-of select="'text (picklist)'"/>"
	</xsl:template>

	<xsl:template mode="typeValue" match="* | @* | text ()">
		""
	</xsl:template>

    <!-- ============================================================================================ -->

	<xsl:template mode="lengthValue" match="sfdc:fields [ sfdc:length ]">
		"<xsl:value-of select="normalize-space ( sfdc:length )"/>"
	</xsl:template>

	<xsl:template mode="lengthValue" match="* | @* | text ()">
		""
	</xsl:template>

    <!-- ============================================================================================ -->

</xsl:stylesheet>