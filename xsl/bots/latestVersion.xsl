<?xml version="1.0"?>
<xsl:transform version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:sfdc="http://soap.sforce.com/2006/04/metadata">
    <xsl:param name="pInclude"/>

	<xsl:template match="/">
		<xsl:apply-templates mode="copy-mode" select="."/>
	</xsl:template>

    <xsl:template mode="copy-mode" match="sfdc:botVersion/sfdc:fullName[$pInclude]">
        <xsl:copy>
            <xsl:apply-templates mode="copy-mode"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template mode="copy-mode" match="sfdc:botVersion/sfdc:fullName"/>

    <xsl:template mode="copy-mode" match="* | @* | text()">
        <xsl:copy>
            <xsl:apply-templates mode="copy-mode"/>
        </xsl:copy>
    </xsl:template>

</xsl:transform>