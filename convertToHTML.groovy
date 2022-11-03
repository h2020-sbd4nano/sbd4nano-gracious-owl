
// input
parser = new groovy.json.JsonSlurper();
def termList = parser.parse(new File("blueprint_20210928.json"))

// output (just to STDOUT for now)

println """<html>
  <head>
    <title>GRACIOUS framework blueprint</title>
  </head>
"""

println """<body>
<h1>GRACIOUS framework blueprint</h1>
<p>
This document is a HTML translation of the GRACIOUS framework blueprint (Traas, Lion, & Vanhauten, Ralph. (2021). doi:<a href="https://doi.org/10.5281/zenodo.6140453">10.5281/zenodo.6140453</a>).
This work is part of <a href="https://www.sbd4nano.eu/">SbD4Nano</a> and has received funding from the European Union’s Horizon 2020 research and innovation program under
grant agreement No. <a href="https://cordis.europa.eu/project/id/862195">862195</a>.
</p>
"""
// endpoints
int counter = 0
for (kb in termList.knowledgebases) {
  if (kb.name == "ENDPOINT_KB") {
    for (category in kb.enumerations) {
      counter++;
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
        cathDescription = note
      } else {
        cathDescription = "-"
      }
      println """
  <h2 id=\"${cathID}\">${cathName}</h2>
  <p>
    <ul>
      <li>Description: ${cathDescription}</li>
      <li>IRI: <a href="${cathIRI}">${cathIRI}</a></li>
      <li><i>owl:sameAs</i> ${sameAsIRI}</li>
    </ul>
  </p>
  <ul>
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
          epDescription = note
        } else {
          epDescription = "-"
        }
        println """
    <h3 id=\"${epID}\">${epName}</h3>
    <p>
      <ul>
        <li>Description: ${epDescription}</li>
        <li>IRI: <a href="${epIRI}">${epIRI}</a></li>
        <li><i>owl:sameAs</i> ${epSameAsIRI}</li>
      </ul>
    </p>
"""    
      }
      println """
  </ul>
"""    
    }
  }
}
println """
</html>
"""
