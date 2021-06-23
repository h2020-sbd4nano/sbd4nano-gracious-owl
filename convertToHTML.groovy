
// input
XmlParser parser = new XmlParser()
def termList = parser.parse (new FileInputStream("DescriptorFramework.xml"))

// output (just to STDOUT for now)


println """<html>
"""

// endpoints
int counter = 0
termList.'endpoint_descriptors'.'endpoint_category'.each { cath ->
  cathName = cath.'@name'
  counter++;
  cathID = (""+counter).padLeft(8,'0')
  cathDescription = cath.definition.text().trim().replaceAll("<", "&lt;")
  println """
  <h2>${cathName}</h2>
  <p>
    <ul>
      <li>Description: ${cathDescription}</li>
      <li>IRI: http://www.bigcat.unimaas.nl/sbd4nano/gracious.owl#GR_${cathID}</li>
    </ul>
  </p>
  <ul>
"""
  cath.'result_endpoints'.'result_endpoint'.each { ep ->
    epName = ep.'@name'
    counter++;
    epID = (""+counter).padLeft(8,'0')
    epDescription = ep.definition.text().trim().replaceAll("<", "&lt;").replaceAll("&", "&amp;")
    println """
    <h3>${epName}</h3>
    <p>
      <ul>
        <li>Description: ${epDescription}</li>
        <li>IRI: http://www.bigcat.unimaas.nl/sbd4nano/gracious.owl#GR_${epID}</li>
      </ul>
    </p>
"""    
  }
  println """
  </ul>
"""    
}

println """
</html>
"""
