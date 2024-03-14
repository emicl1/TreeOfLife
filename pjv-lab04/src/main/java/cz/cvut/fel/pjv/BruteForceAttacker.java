package cz.cvut.fel.pjv;

public class BruteForceAttacker extends Thief {

    @Override
    public void breakPassword(int sizeOfPassword) {
        generatePasswords(getCharacters(), "", sizeOfPassword);
    }

    public boolean generatePasswords(char[] chars, String current, int size) {
        if (current.length() == size) {
            tryOpen(current.toCharArray());
            if (isOpened()) {
                return true;
            }
            return false;
        }
        for (char aChar : chars) {
            String newCombination = current + aChar;
            if (generatePasswords(chars, newCombination, size)){
                return true;
            }
        }
        return false;
    }
}

