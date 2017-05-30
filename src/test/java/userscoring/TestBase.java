package userscoring;

public class TestBase {
    TestData[] testData;

    public TestBase() {
        testData = new TestData[]{
                new TestData(18, "Riga", "Latvia", 0),
                new TestData(22, "Liepaja", "Latvia", 2),
                new TestData(23, "Riga", "Latvia", 0),
                new TestData(35, "Valmiera", "Latvia", 2),
                new TestData(36, "Riga", "Latvia", 0),
                new TestData(50, "Daugavpils", "Latvia", 2),
                new TestData(51, "Riga", "Latvia", 2),
                new TestData(60, "Liepaja", "Latvia", 4),
                new TestData(61, "Riga", "Latvia", 2),
                new TestData(75, "Valmiera", "Latvia", 4),
        };
    }

    class TestData {
        int age;
        String city;
        String country;
        int children;
        int expectedScore;

        TestData(int age, String city, String country, int children) {
            this.age = age;
            this.city = city;
            this.country = country;
            this.children = children;
            this.expectedScore = calculateExpectedScore();
        }

        private int calculateExpectedScore() {
            int ageScore;
            int cityScore;
            int countryScore;
            int childScore;
            if (age >= 18 && age <= 22) {
                ageScore = 200;
            } else if (age >= 23 && age <= 35) {
                ageScore = 300;
            } else if (age >= 36 && age <= 50) {
                ageScore = 250;
            } else if (age >= 51 && age <= 60) {
                ageScore = 200;
            } else
                ageScore = 100;

            if (city.equals("Riga")) {
                cityScore = 300;
            } else
                cityScore = 100;

            if (country.equals("Latvia")) {
                countryScore = 300;
            } else
                countryScore = 100;

            if (children >= 0 && children <= 1) {
                childScore = 300;
            } else if (children >= 2 && children <= 3) {
                childScore = 200;
            } else
                childScore = 100;

            expectedScore = ageScore + cityScore + countryScore + childScore;
            return expectedScore;
        }
    }
}
