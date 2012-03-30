<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:sfdc="http://soap.sforce.com/2006/04/metadata">
  <xsl:param name="name" />
  <xsl:output method="text" omit-xml-declaration="yes" />

  <xsl:template match="/">
	CREATE TABLE <xsl:value-of select="$name" />
	(
	<xsl:apply-templates select="sfdc:CustomObject" />
	)
  </xsl:template>

  <xsl:template match="sfdc:CustomObject" >
	<xsl:apply-templates select="sfdc:fields" />
  </xsl:template>

  <xsl:template match="sfdc:fields[sfdc:type/text() = 'AutoNumber']">
	<xsl:value-of select="concat(sfdc:fullName, ' ', sfdc:type)" />
	<xsl:apply-templates select="." mode="comma" />
  </xsl:template>

  <xsl:template match="sfdc:fields[sfdc:type/text() = 'Checkbox']">
	<xsl:value-of select="concat(sfdc:fullName, ' ', sfdc:type)" />
	<xsl:apply-templates select="." mode="comma" />
  </xsl:template>

  <xsl:template match="sfdc:fields[sfdc:type/text() = 'Date']">
	<xsl:value-of select="concat(sfdc:fullName, ' ', sfdc:type)" />
	<xsl:apply-templates select="." mode="comma" />
  </xsl:template>

  <xsl:template match="sfdc:fields[sfdc:type/text() = 'DateTime']">
	<xsl:value-of select="concat(sfdc:fullName, ' ', sfdc:type)" />
	<xsl:apply-templates select="." mode="comma" />
  </xsl:template>

  <xsl:template match="sfdc:fields[sfdc:type/text() = 'Email']">
	<xsl:value-of select="concat(sfdc:fullName, ' ', sfdc:type)" />
	<xsl:apply-templates select="." mode="comma" />
  </xsl:template>

  <xsl:template match="sfdc:fields[sfdc:type/text() = 'EncryptedText']">
	<xsl:value-of select="concat(sfdc:fullName, ' ', sfdc:type)" />
	<xsl:apply-templates select="." mode="comma" />
  </xsl:template>

  <xsl:template match="sfdc:fields[sfdc:type/text() = 'Html']">
	<xsl:value-of select="concat(sfdc:fullName, ' ', sfdc:type)" />
	<xsl:apply-templates select="." mode="comma" />
  </xsl:template>

  <xsl:template match="sfdc:fields[sfdc:type/text() = 'LongTextArea']">
	<xsl:value-of select="concat(sfdc:fullName, ' ', sfdc:type)" />
	<xsl:apply-templates select="." mode="comma" />
  </xsl:template>

  <xsl:template match="sfdc:fields[sfdc:type/text() = 'Lookup']">
	<xsl:value-of select="concat(sfdc:fullName, ' ', sfdc:type)" />
	<xsl:apply-templates select="." mode="comma" />
  </xsl:template>

  <xsl:template match="sfdc:fields[sfdc:type/text() = 'MasterDetail']">
	<xsl:value-of select="concat(sfdc:fullName, ' ', sfdc:type)" />
	<xsl:apply-templates select="." mode="comma" />
  </xsl:template>

  <xsl:template match="sfdc:fields[sfdc:type/text() = 'MultiselectPicklist']">
	<xsl:value-of select="concat(sfdc:fullName, ' ', sfdc:type)" />
	<xsl:apply-templates select="." mode="comma" />
  </xsl:template>

  <xsl:template match="sfdc:fields[sfdc:type/text() = 'Number']">
	<xsl:value-of select="concat(sfdc:fullName, ' ', sfdc:type)" />
	<xsl:apply-templates select="." mode="comma" />
  </xsl:template>

  <xsl:template match="sfdc:fields[sfdc:type/text() = 'Percent']">
	<xsl:value-of select="concat(sfdc:fullName, ' ', sfdc:type)" />
	<xsl:apply-templates select="." mode="comma" />
  </xsl:template>

  <xsl:template match="sfdc:fields[sfdc:type/text() = 'Phone']">
	<xsl:value-of select="concat(sfdc:fullName, ' ', sfdc:type)" />
	<xsl:apply-templates select="." mode="comma" />
  </xsl:template>

  <xsl:template match="sfdc:fields[sfdc:type/text() = 'Picklist']">
	<xsl:value-of select="concat(sfdc:fullName, ' ', sfdc:type)" />
	<xsl:apply-templates select="." mode="comma" />
  </xsl:template>

  <xsl:template match="sfdc:fields[sfdc:type/text() = 'Summary']">
	<xsl:value-of select="concat(sfdc:fullName, ' ', sfdc:type)" />
	<xsl:apply-templates select="." mode="comma" />
  </xsl:template>

  <xsl:template match="sfdc:fields[sfdc:type/text() = 'Text']">
	<xsl:value-of select="concat(sfdc:fullName, ' ', sfdc:type)" />
	<xsl:apply-templates select="." mode="comma" />
  </xsl:template>

  <xsl:template match="sfdc:fields[sfdc:type/text() = 'TextArea']">
	<xsl:value-of select="concat(sfdc:fullName, ' ', sfdc:type)" />
	<xsl:apply-templates select="." mode="comma" />
  </xsl:template>

  <xsl:template match="sfdc:fields[sfdc:type/text() = 'Url']">
	<xsl:value-of select="concat(sfdc:fullName, ' ', sfdc:type)" />
	<xsl:apply-templates select="." mode="comma" />
  </xsl:template>

  <xsl:template match="sfdc:fields[following-sibling::sfdc:fields]" mode="comma">,<xsl:text>
  </xsl:text></xsl:template>

  <xsl:template match="sfdc:fields" mode="comma"></xsl:template>

  <xsl:template match="sfdc:fields">
	TODO unimplemented type: <xsl:value-of select="sfdc:type" />
  </xsl:template>

</xsl:stylesheet>
