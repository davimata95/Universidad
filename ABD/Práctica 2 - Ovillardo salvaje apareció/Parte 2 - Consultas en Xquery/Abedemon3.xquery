xquery version "3.0";

declare variable $id as xs:string external;
    
for $b in doc("Abedemon.xml")//especie[@id = $id]
where $b/@id = $id
return 
    <html>
        <head></head>
        <body>
            <h1> {$b/nombre} </h1>
            <img src="{$b/url}"/>
            <p> {data($b/descripcion)} </p>
            <p><b>Tipos: </b> {string-join($b/tipo, ", ")} </p>
            <p><b>Ataques: </b> 
                {string-join(for $a in doc("Abedemon.xml")/bd-abedemon/ataque
                 where $a/@id = $b/ataques/tiene-ataque/@id
                 order by $a/nombre
                 return $a/nombre, ", ")
                }
            </p>
            <p><b>Evoluciones: </b>
                <ul>
                    {for $c in $b//especie
                     return <li><a href="{$c/@id}"> {$c/nombre} </a></li>
                    }
                </ul>
            </p>
        </body>
    </html>