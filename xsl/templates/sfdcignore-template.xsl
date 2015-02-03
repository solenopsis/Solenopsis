<?xml version= "1.0" encoding= "UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:sfdc="http://soap.sforce.com/2006/04/metadata" exclude-result-prefixes="xsl xs fn">

    <!--
        ============================================================================================

        This XSLT is a template XSLT for how to ignore values found in the sfdcignore file.
        An external script needs to provide the pieces to ignore by substituting for the IGNORE
        (we are surrounding it with double at signs) and executing the XSLT.  This template
        version of the XSLT cannot be called directly without substitution.

        Examples:
            Ignore the class Foo.cls
                xsl:template mode="copy-children" match="sfdc:classAccesses [sfdc:apexClass [normalize-space(text()) = 'Foo']]"

            Ignore the layout FeedItem-Feed Item Layout.layout
                xsl:template mode="copy-children" match="sfdc:layoutAssignments [sfdc:layout [normalize-space(text()) = 'FeedItem-Feed Item Layout]]"

        We templatized this so we could build up all the elements to ignore vs calling this transform
        over and over again for each thing to ignore.

        The key to all this is using these empty templates:
            xsl:template mode="copy-children" match="sfdc:layoutAssignments [sfdc:layout [normalize-space(text()) = $pNAME]]"/
            xsl:template mode="copy-children" match="sfdc:classAccesses [sfdc:apexClass [normalize-space(text()) = $pNAME]]"/

        ============================================================================================
    -->

    <xsl:output method="xml" version="1.0" indent="yes"/>

    <!-- ============================================================================================ -->

    <xsl:template match="* | @* | text()"/>

    <!-- ============================================================================================ -->

    <xsl:template match="/">
        <!-- Hate to do this, but preserving formatting -->
<xsl:text>
</xsl:text>

        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="sfdc:Profile | sfdc:PermissionSet">
        <xsl:copy>
            <xsl:apply-templates mode="copy-children"/>
        </xsl:copy>
    </xsl:template>

    @@IGNORE@@
    <xsl:template mode="copy-children" match="@* | text()"/>

    <xsl:template mode="copy-children" match="*">
        <xsl:text>
    </xsl:text><xsl:copy-of select="."/>
    </xsl:template>

    <!-- ============================================================================================ -->

</xsl:stylesheet>