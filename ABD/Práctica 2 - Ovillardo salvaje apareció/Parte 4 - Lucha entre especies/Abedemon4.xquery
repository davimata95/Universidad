xquery version "3.0";

declare variable $yoId as xs:string external;
declare variable $adversarioId as xs:string external;

(: Me quedo mis ataques :)
for $yo in doc("Abedemon.xml")//especie[@id = $yoId or @id = $adversarioId]
let $yoAtaques := $yo/ataques/tiene-ataque/@id
where $yo/@id = $yoId
return
	(: Me quedo con ataques del adversario :)
    for $adversario in doc("Abedemon.xml")//especie
    let $adversarioTipo := $adversario/tipo
    where $adversario/@id = $adversarioId
    return max(
    	(: Me quedo con el máximo daño hacia mi adversario :)
        for $ataques in doc("Abedemon.xml")/bd-abedemon/ataque
        let $maxValor := $ataques/daño[@tipo = $adversarioTipo]/@valor
        where $ataques/daño/@tipo = $adversarioTipo
        and $ataques/@id = $yoAtaques
        return $maxValor)