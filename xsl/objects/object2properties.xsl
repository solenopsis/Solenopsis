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

	<!--
		This XSLT generates a properties file for an object file...

		qa.properties = Account_Alias__c
		qa.properties.type.Account_Alias__c=Text
		qa.properties.length.Account_Alias__c=240
	-->

	<xsl:param name="pPROPERTY_PREFIX"/>
	<xsl:param name="pNAME"/>

    <!-- ============================================================================================ -->

	<xsl:variable name="vFIELDS_PREFIX"        select="concat ( $pPROPERTY_PREFIX, '.fields.', $pNAME )"/>
	<xsl:variable name="vFIELDS_TYPE_PREFIX"   select="concat ( $pPROPERTY_PREFIX, '.field.type.',   $pNAME, '.' )"/>
	<xsl:variable name="vFIELDS_VALUE_PREFIX"  select="concat ( $pPROPERTY_PREFIX, '.field.value.',  $pNAME, '.' )"/>
	<xsl:variable name="vFIELDS_SIZE_PREFIX"   select="concat ( $pPROPERTY_PREFIX, '.field.size.',   $pNAME, '.' )"/>

    <!-- ============================================================================================ -->

    <xsl:template match="* | @* | text()"/>

    <!-- ============================================================================================ -->

    <xsl:template match="/">
		<xsl:variable name="vRAW_PROPERTIES">
			<xsl:apply-templates mode="concatValues" select="sfdc:CustomObject/sfdc:fields/sfdc:fullName">
				<xsl:sort select="."/>
			</xsl:apply-templates>
		</xsl:variable>

		<xsl:variable name="vPROPERTIES" select="normalize-space ( $vRAW_PROPERTIES )"/>

# All the fields defined...
<xsl:text>
</xsl:text>
<xsl:value-of select="concat ( $vFIELDS_PREFIX, ' = ', substring ( $vPROPERTIES, 1, string-length ( $vPROPERTIES ) - 1 ) )"/>

# All the types for the fields...
<xsl:text>
</xsl:text>
		<xsl:apply-templates mode="emitTypes" select="sfdc:CustomObject/sfdc:fields">
			<xsl:sort select="sfdc:fullName"/>
		</xsl:apply-templates>

# All the values for the fields...
<xsl:text>
</xsl:text>
		<xsl:apply-templates mode="emitValues" select="sfdc:CustomObject/sfdc:fields">
			<xsl:sort select="sfdc:fullName"/>
		</xsl:apply-templates>

# All the sizes for the fields...
<xsl:text>
</xsl:text>
		<xsl:apply-templates mode="emitSizes" select="sfdc:CustomObject/sfdc:fields">
			<xsl:sort select="sfdc:fullName"/>
		</xsl:apply-templates>
    </xsl:template>

    <!-- ============================================================================================ -->

	<xsl:template mode="concatValues" match="*"><xsl:value-of select="normalize-space ( . )"/>,</xsl:template>

    <!-- ============================================================================================ -->

<xsl:template mode="emitTypes" match="sfdc:fields [ normalize-space ( sfdc:type ) != '' ]">
<xsl:value-of select="concat ( $vFIELDS_TYPE_PREFIX, normalize-space ( sfdc:fullName ), ' = ', normalize-space ( sfdc:type ) )"/>
<xsl:text>
</xsl:text>
</xsl:template>

	<xsl:template mode="emitTypes" match="* | @* | text ()"/>

    <!-- ============================================================================================ -->

	<xsl:template mode="emitValues" match="sfdc:fields [ sfdc:picklist/sfdc:picklistValues ]">
		<xsl:variable name="vRAW_PROPERTIES">
			<xsl:apply-templates mode="concatValues" select="sfdc:picklist/sfdc:picklistValues/sfdc:fullName">
				<xsl:sort select="."/>
			</xsl:apply-templates>
		</xsl:variable>

		<xsl:variable name="vPROPERTIES" select="normalize-space ( $vRAW_PROPERTIES )"/>

<xsl:value-of select="concat ( $vFIELDS_VALUE_PREFIX, normalize-space ( sfdc:fullName ), ' = ', substring ( $vPROPERTIES, 1, string-length ( $vPROPERTIES ) - 1 ) )"/>
<xsl:text>
</xsl:text>
	</xsl:template>


	<xsl:template mode="emitValues" match="* | @* | text ()"/>

    <!-- ============================================================================================ -->

<xsl:template mode="emitSizes" match="sfdc:fields [ normalize-space ( sfdc:length ) != '' ]">
<xsl:value-of select="concat ( $vFIELDS_SIZE_PREFIX, normalize-space ( sfdc:fullName ), ' = ', normalize-space ( sfdc:length ) )"/>
<xsl:text>
</xsl:text>
</xsl:template>

	<xsl:template mode="emitSizes" match="* | @* | text ()"/>

    <!-- ============================================================================================ -->

</xsl:stylesheet>