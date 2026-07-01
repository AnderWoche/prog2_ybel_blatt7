# Generics

### Wo helfen Ihnen die Generics im Zoo-Szenario, Fehler bereits zur Compile-Zeit zu vermeiden?

Wenn man z. B. nur eine bestimmte Klasse von Cat haben möchte, so wie wir es beim CatHouse gemacht haben, dann ist das mit Generics sauber und eindeutig, ohne dass man selbst prüfen müsste oder einfach im Code aufpassen müsste, keine Logikfehler zu machen, damit das funktioniert.
Außerdem kann man mit Generics schon zur Compile-Zeit festlegen, welche Typen erlaubt sind. In C hätte man einen void*-Pointer und könnte damit auf alles zeigen.

### Nennen Sie ein Beispiel aus Ihrer Implementierung, bei dem falsche Tier-Gehege-Kombinationen durch den Typchecker verhindert werden.

Wenn wir z. B. ein Fish-Gehege erstellen, kann man dort keine Tiger mehr hineinpacken, weil nur Klassen, die von Fish erben, dort hineinkönnen.

# Logging

### Warum ist systematisches Logging mit einem Logger und Log-Leveln für ein Zoo-Management-System sinnvoller als Ausgaben mit IO.println?

Weil man einstellen kann, wie genau die Logs sein sollen. Und bei einem komplexen Management-System muss man auch mal debuggen, und mit guten Logs kann man dann eine gute Data-Mining-Analyse durchführen, um gegebenenfalls Fehler zu entdecken. Bei reinem System.out kann man das nicht ausschalten und müsste immer wieder alles neu schreiben, nachdem man es gelöscht hat.


### In welchen Situationen würden Sie in Ihrem System die Log-Level INFO, WARNING und ggf. SEVERE verwenden?

1. SEVERE bei Vorstellungen vor Publikum, bei denen man zeigen möchte, wie fehlerfrei es ist.
2. WARNING bei regulärem Betrieb.
3. INFO während der Entwicklung.

# Streams

### Wo haben Ihnen Streams im Vergleich zu klassischen Schleifen beim Formulieren Ihrer Abfragen geholfen? Wo wurde es eher unübersichtlich?

Streams waren super zum Rausfiltern und Mappen der Objekte, ohne SuppressWarnings zu kriegen. getAllAnimals().stream()
.collect(Collectors.groupingBy(Animal::getClass, Collectors.counting())); das war etwas ungewöhnlich, aber man muss die Methoden einfach lernen und sich daran gewöhnen.
