package de.david.zoo.animal;

public sealed interface Animal {

    public String getName();

    public sealed interface Mammal extends Animal {
        sealed interface Primate extends Mammal permits Gorilla, Chimpanzee {

        }
        sealed interface Rodent extends Mammal permits Mouse, Squirrel {

        }
        sealed interface Cat extends Mammal permits Lion, Tiger {

        }

    }

    public sealed interface Fish extends Animal permits Trout, Salmon {

    }

    public sealed interface Reptile extends Animal permits Snake, Turtle {

    }

    public sealed interface Bird extends Animal permits Eagle, Parrot {

    }

}