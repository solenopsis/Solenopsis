<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : object.xsl
    Created on : March 31, 2012, 2:40 PM
    Author     : sfloess
    Description:
        Convert SFDC object XML into Java properties file.  Good for SFDC API 24.0.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns="http://soap.sforce.com/2006/04/metadata">
    <xsl:output method="text"/>
    
    <xsl:template name="EmitProperty">
        <xsl:param name="pPROPERTY_NAME"/>
        <xsl:param name="pVALUE"/>
<xsl:value-of select="$pPROPERTY_NAME"> = <xsl:value-of select="normalize-space($pVALUE)"/>  
    </xsl:template>

    <xsl:template match="/">
        <xsl:apply-templates select="CustomObject [actionOverrides   [actionName != '']]" mode="ActionOverrideProperty"/>
        <xsl:apply-templates select="CustomObject [businessProcesses [fullName   != '']]" mode="BusinessProcessProperty"/>
        <xsl:apply-templates select="CustomObject [fields            [fullName   != '']]" mode="CustomFieldProperty"/>
        <xsl:apply-templates select="CustomObject [listViews         [fullName   != '']]" mode="ListViewProperty"/>
        <xsl:apply-templates select="CustomObject [namedFilters      [fullName   != '']]" mode="NamedFilterProperty"/>
        <xsl:apply-templates select="CustomObject [recordTypes       [fullName   != '']]" mode="RecordTypeProperty"/>
        <xsl:apply-templates select="CustomObject [validationRules   [fullName   != '']]" mode="ValidationRuleProperty"/>
        <xsl:apply-templates select="CustomObject [webLinks          [fullName   != '']]" mode="WebLinkProperty"/> 
    </xsl:template>
    
    <xsl:template match="CustomObject" mode="ActionOverrideProperty">
        <xsl:call-template name="EmitProperty">
            <xsl:with-param name="pPROPERTY" select="'ActionOverride'"/>
            <xsl:with-param name="pVALUE">
                <xsl:apply-templates select="actionOverrides/actionName" mode="Values"/>
            </xsl:with-param>
        </xsl:call-template>      
    </xsl:template>
    
    <xsl:template match="CustomObject" mode="BusinessProcessProperty">
        <xsl:call-template name="EmitProperty">
            <xsl:with-param name="pPROPERTY" select="'BusinessProcess'"/>
            <xsl:with-param name="pVALUE">
                <xsl:apply-templates select="businessProcesses/fullName" mode="Values"/>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template match="CustomObject" mode="CustomFieldProperty">
        <xsl:call-template name="EmitProperty">
            <xsl:with-param name="pPROPERTY" select="'CustomField'"/>
            <xsl:with-param name="pVALUE">
                <xsl:apply-templates select="fields/fullName" mode="Values"/>
            </xsl:with-param>
        </xsl:call-template>   
    </xsl:template>
    
    <xsl:template match="CustomObject" mode="ListViewProperty">
        <xsl:call-template name="EmitProperty">
            <xsl:with-param name="pPROPERTY" select="'ListView'"/>
            <xsl:with-param name="pVALUE">
                <xsl:apply-templates select="istViews/fullName" mode="Values"/>
            </xsl:with-param>
        </xsl:call-template>               
    </xsl:template>
    
    <xsl:template match="CustomObject" mode="NamedFilterProperty">
        <xsl:call-template name="EmitProperty">
            <xsl:with-param name="pPROPERTY" select="'NamedFilter'"/>
            <xsl:with-param name="pVALUE">
                <xsl:apply-templates select="namedFilters/fullName" mode="Values"/>
            </xsl:with-param>
        </xsl:call-template>                
    </xsl:template>
    
    <xsl:template match="CustomObject" mode="RecordTypeProperty">
        <xsl:call-template name="EmitProperty">
            <xsl:with-param name="pPROPERTY" select="'RecordType'"/>
            <xsl:with-param name="pVALUE">
                <xsl:apply-templates select="recordTypes/fullName" mode="Values"/>
            </xsl:with-param>
        </xsl:call-template>               
    </xsl:template>
    
    <xsl:template match="CustomObject" mode="ValidationRuleProperty">
        <xsl:call-template name="EmitProperty">
            <xsl:with-param name="pPROPERTY" select="'ValidationRule'"/>
            <xsl:with-param name="pVALUE">
                <xsl:apply-templates select="validationRules/fullName" mode="Values"/>
            </xsl:with-param>
        </xsl:call-template>        
    </xsl:template>
    
    <xsl:template match="CustomObject" mode="WebLinkProperty">
        <xsl:call-template name="EmitProperty">
            <xsl:with-param name="pPROPERTY" select="'WebLink'"/>
            <xsl:with-param name="pVALUE">
                <xsl:apply-templates select="webLinks/fullName" mode="Values"/>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>
    
    <xsl:template match="actionName | fullName" mode="Values">
        <xsl:value-of select = "."/>
    </xsl:template>
</xsl:stylesheet>
