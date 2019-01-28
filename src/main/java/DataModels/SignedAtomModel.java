package DataModels;

/**
 * Атом со знаком
 */
public class SignedAtomModel {
    private boolean IsNegative = false;

    private AtomModel Atom;

    public void setNegative(boolean negative) {
        IsNegative = negative;
    }

    public boolean isNegative(){
        return IsNegative;
    }

    public void setAtom(AtomModel atom) {
        Atom = atom;
    }

    public AtomModel getAtom(){
        return Atom;
    }

    @Override
    public String toString() {
        String str = "";
        if (IsNegative){
            str = "-";
        }
        str += Atom.toString();
        return str;
    }
}
