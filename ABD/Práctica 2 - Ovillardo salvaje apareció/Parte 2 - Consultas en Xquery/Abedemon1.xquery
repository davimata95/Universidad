xquery version "3.0";

for $b in distinct-values(doc("Abedemon.xml")//tipo)
return $b