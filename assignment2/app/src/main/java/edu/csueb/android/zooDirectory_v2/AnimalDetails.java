package edu.csueb.android.zooDirectory_v2;

class AnimalDetails {
    String animalName;
    String animalDescription;
    int animalImage;

    public AnimalDetails(String animalName, String animalDescription, int animalImage) {
        this.animalName = animalName;
        this.animalDescription = animalDescription;
        this.animalImage = animalImage;
    }

    public String getAnimalName() {
        return animalName;
    }

    public String getAnimalDescription() {
        return animalDescription;
    }

    public int getAnimalImage() { return animalImage; }
}
