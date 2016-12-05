xquery version "3.0";

declare variable $tipo as xs:string external;

for $b in doc("Abedemon.xml")//especie[tipo = $tipo]
let $numAtaques := count($b/ataques/tiene-ataque)
where $b/tipo = $tipo
return <resultado
    id = "{$b/@id}"
    nombre = "{$b/nombre}"
    num-ataques = "{$numAtaques}">
    </resultado>