# Generics

### Wo helfen Ihnen die Generics im Zoo-Szenario, Fehler bereits zur Compile-Zeit zu vermeiden?

Wenn man z.B nur eine bestimmte klasse haben möchte von Cat wie wir es bei dem CatHouse Gemacht haebn möchtel. dann ist das mit generist sauber und eindeutig ohne das müsste man selber checkts oder einfacch im code keine logic fehler machen damit das fukntioniert.
auserdem kann man mit generiks schon in der Compile zeit festlegen welche typen erlaub sind in C hätte man ein Void* pointer und man könnte auf alles zeigen.

### Nennen Sie ein Beispiel aus Ihrer Implementierung, bei dem falsche Tier-Gehege-Kombinationen durch den Typchecker verhindert werden.

Z.b wenn wir ein Fish gehege erstellen kann man da keine Tiger mehr rein packen. weil nur classen die von fish ergeben da rein können.

# Logging

### Warum ist systematisches Logging mit einem Logger und Log-Leveln für ein Zoo-Management-System sinnvoller als Ausgaben mit IO.println?

Weil man einstellen kann wie genau die logs sein sollen. Und bei einemn Komplexen management system muss man auch mal debuggen und mit guten logs kann man dann eine gute Data Mining analyse druchführen um gegebenen falls fehjler zu entdecken. Bei nur sysout kann man die nciht ausschalten. und müsste immer weider alles neu sschreiben nach dem löschen.


### In welchen Situationen würden Sie in Ihrem System die Log-Level INFO, WARNING und ggf. SEVERE verwenden?

1. SERVE bei vorstellungen im publicum wo man zeigen möchte wie fehler frei es ist.
2. WARNUNG bei regulären betrieb
3. INFO bei entwicklen.

# Streams

### Wo haben Ihnen Streams im Vergleich zu klassischen Schleifen beim Formulieren Ihrer Abfragen geholfen? Wo wurde es eher unübersichtlich?

streams warhen super zum rausfiltern und mappen der obejcte ohne Supresswarnings zu kreiegn. getAllAnimals().stream()
.collect(Collectors.groupingBy(Animal::getClass, Collectors.counting())); das war estwas ungewöhnlich aber man muss die methdoen einfach lernen und sich daran gewöhnen.

