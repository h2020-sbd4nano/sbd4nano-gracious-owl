
// input
parser = new groovy.json.JsonSlurper();
def termList = parser.parse(new File("blueprint_20210928.json"))

// output (just to STDOUT for now)

println """<?xml version="1.0"?>
<rdf:RDF xmlns="http://www.bigcat.unimaas.nl/sbd4nano/gracious.owl#"
     xml:base="http://www.bigcat.unimaas.nl/sbd4nano/gracious.owl"
     xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
     xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
     xmlns:obo="http://purl.obolibrary.org/obo/"
     xmlns:owl="http://www.w3.org/2002/07/owl#">
  <owl:Ontology rdf:about="http://www.bigcat.unimaas.nl/sbd4nano/gracious.owl">
  </owl:Ontology>
"""

// endpoints
int counter = 0
for (kb in termList.knowledgebases) {
  if (kb.name == "ENDPOINT_KB" || kb.name == "IATA_KB") {
    for (category in kb.enumerations) {
      cathName = category.name
      if (category.id != null) {
        cathID = category.id
      } else {
        counter++;
        cathID = (""+counter).padLeft(8,'0')
      }
      cathIRI = "https://h2020-sbd4nano.github.io/sbd4nano-gracious-owl/gracious.html#${cathID}"
      sameAsIRI = "https://h2020-sbd4nano.github.io/gracious-blueprint/2021/${cathName}"
      if (category.note != null) {
        note = category.note.trim().replaceAll("<", "&lt;")
        cathDescription = """
        <obo:IAO_0000115>${note}</obo:IAO_0000115>"""
      } else {
        cathDescription = ""
      }
      println """
    <owl:Class rdf:about="${cathIRI}">
        <rdfs:label xml:lang="en">${cathName}</rdfs:label>${cathDescription}
        <owl:sameAs rdf:resource="${sameAsIRI}"/>
    </owl:Class>
"""
      for (ep in category.enumerates) {
        epName = ep.name
        if (ep.id != null) {
          epID = ep.id
        } else {
          counter++;
          epID = (""+counter).padLeft(8,'0')
        }
        epIRI = "https://h2020-sbd4nano.github.io/sbd4nano-gracious-owl/gracious.html#${epID}"
        epSameAsIRI = "https://h2020-sbd4nano.github.io/gracious-blueprint/2021/${epName}"
        if (ep.note != null) {
          if (ep.note instanceof String) {
            note = ep.note.trim().replaceAll("<", "&lt;").replaceAll("&", "&amp;")
          } else {
            note = ep.note.definition.text.trim().replaceAll("<", "&lt;").replaceAll("&", "&amp;")
          }
          epDescription = """
        <obo:IAO_0000115>${note}</obo:IAO_0000115>"""
        } else {
          epDescription = ""
        }
        println """
    <owl:Class rdf:about="${epIRI}">
        <rdfs:subClassOf rdf:resource="${cathIRI}"/>
        <rdfs:label xml:lang="en">${epName}</rdfs:label>${epDescription}
        <owl:sameAs rdf:resource="${epSameAsIRI}"/>
    </owl:Class>
"""
      }
    }
  }
}

println """
</rdf:RDF>
"""
