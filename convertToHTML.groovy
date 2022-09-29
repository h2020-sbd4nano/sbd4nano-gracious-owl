
// input
parser = new groovy.json.JsonSlurper();
def termList = parser.parse(new File("blueprint_20210928.json"))

// output (just to STDOUT for now)

println """<html>
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
      <li>IRI: ${cathIRI}</li>
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
        <li>IRI: ${epIRI}</li>
      </ul>
    </p>
"""    
      }
    }
    println """
  </ul>
"""    
  }
}
println """
</html>
"""
