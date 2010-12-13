<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="container" /> <!-- drop this element for PRONOM -->
	<xsl:template match="FidoSignature" /> <!-- drop this element for PRONOM -->
	<xsl:template match="RelationshipType"> <!-- Remove underscores from enums for PRONOM -->
		<RelationshipType><xsl:value-of select="translate(.,'_',' ')" /></RelationshipType>
	</xsl:template>
	<xsl:template match="SignatureType">
		<SignatureType><xsl:value-of select="translate(.,'_',' ')" /></SignatureType>
	</xsl:template>
	<xsl:template match="IdentifierType">
		<IdentifierType><xsl:value-of select="translate(.,'_',' ')" /></IdentifierType>
	</xsl:template>
	<xsl:template match="node() | @*"> <!-- Copy everything else one-to-one -->
		<xsl:copy>
			<xsl:apply-templates select="node() | @*" />
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>