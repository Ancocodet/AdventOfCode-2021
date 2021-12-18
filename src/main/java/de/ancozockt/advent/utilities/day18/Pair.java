package de.ancozockt.advent.utilities.day18;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Pair extends Element{

    public Element left;
    public Element right;

    public Pair(Pair parent, Element left, Element right) {
        super(parent);
        this.left = left;
        this.right = right;

        left.parent = this;
        right.parent = this;
    }

    public long getMagnitude() {
        return 3 * left.getMagnitude() + 2 * right.getMagnitude();
    }

    @Override
    public Pair clone() {
        return new Pair(null, left.clone(), right.clone());
    }

    public boolean explode() {
        return explode(0);
    }

    private boolean explode(int level) {
        if (level == 4) {
            Literal left = (Literal) this.left;
            Literal right = (Literal) this.right;

            Literal leftLiteral = getLeftLiteral(this);
            if (leftLiteral != null) {
                leftLiteral.value += left.value;
            }
            Literal rightLiteral = getRightLiteral(this);
            if (rightLiteral != null) {
                rightLiteral.value += right.value;
            }


            Literal newLiteral = new Literal(this.parent, 0);
            if (this.parent.left == this) {
                this.parent.left = newLiteral;
            } else {
                this.parent.right = newLiteral;
            }

            return true;
        }
        if (left instanceof Pair left) {
            if (left.explode(level + 1)) {
                return true;
            }
        }
        if (right instanceof Pair right) {
            return right.explode(level + 1);
        }

        return false;
    }

    private Literal getLeftLiteral(Pair pair) {
        Pair current = pair;

        while (current.parent != null && current.parent.getLeft() == current) {
            current = current.parent;
        }

        if (current.parent == null) {
            // Went to the top and nothing on the left
            return null;
        }

        Element e = current.parent.getLeft();
        while (e instanceof Pair p) {
            e = p.getRight();
        }

        return (Literal) e;
    }

    private Literal getRightLiteral(Pair pair) {
        Pair current = pair;

        while (current.parent != null && current.parent.getRight() == current) {
            current = current.parent;
        }

        if (current.parent == null) {
            return null;
        }

        Element element = current.parent.getRight();
        while (element instanceof Pair p) {
            element = p.getLeft();
        }

        return (Literal) element;
    }

    public boolean split() {
        if (left instanceof Literal left) {
            if (left.value >= 10) {
                this.left = left.split();
                return true;
            }
        } else if (left instanceof Pair left) {
            if (left.split()) {
                return true;
            }
        }

        if (right instanceof Literal right) {
            if (right.value >= 10) {
                this.right = right.split();
                return true;
            }
        } else if (right instanceof Pair right) {
            if (right.split()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return Objects.equals(left, pair.getLeft()) && Objects.equals(right, pair.getRight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return "[" + left + "," + right + ']';
    }

}
