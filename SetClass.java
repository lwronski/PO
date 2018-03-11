
public class Set {

    private Set[] createset;

    private Set(int i) {
        this.createset = new Set[i];
    }

    private static Set empty = new Set(0);

    public static Set newSet() {
        Set create = empty;
        return create;
    }

    private static boolean compare( Set first, Set second)
    {
        int term =0;
        if ( first.createset.length != second.createset.length) return false;
        else
        {
            for ( int i = 0; i < first.createset.length; i++ )
            {
                term = 0;
                for ( int j = 0; j < first.createset.length; j++)
                {
                    if(compare(first.createset[i],second.createset[j]))
                    {
                        term = 1;
                        break;
                    }
                }
                if( term == 0)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public static Set newSet(Set... varargs) {

        Set temp =  new Set(varargs.length);
        int term;
        int counter = 0;

        for (int i = 0; i < varargs.length; i++)
        {
            term = 0;
            for (int j = 0; j < counter; j++)
            {
                if (compare(varargs[i],temp.createset[j]))
                {
                    term = 1;
                    break;
                }
            }
            if (term == 0)
            {
                temp.createset[counter] = varargs[i];
                counter++;
            }
        }
        Set create = new Set(counter);
        for ( int i = 0; i< counter; i++ )
        {
            create.createset[i] = temp.createset[i];
        }
        return create;
    }

    public static Set union(Set edit)
    {
        Set check;
        int counter = 0;
        int term = 0;

        for ( int i = 0; i<edit.createset.length; i++)
        {
            counter += edit.createset[i].createset.length;
        }
        Set[] copy = new Set[counter];
        counter = 0;
        for (int i = 0; i < edit.createset.length; i++)
        {
            check = edit.createset[i];
            for (int j = 0; j < check.createset.length; j++)
            {
                term =0;
                for ( int l = 0; l<counter; l++)
                {
                    if(compare(copy[l],check.createset[j]))
                    {
                        term = 1;
                        break;
                    }
                }
                if(term == 0)
                {
                    copy[counter] = check.createset[j];
                    counter++;
                }
            }
        }
        Set create = new Set(counter);  // przepisywanie
        for ( int i = 0; i<counter; i++)
        {
            create.createset[i] = copy[i];
        }
        return create;
    }

    public java.lang.String toString() {
        String out = "{";
        int counter = 0;
        for (Object item : this.createset) {
            if (counter > 0 && counter < this.createset.length) {
                out += ", ";
            }
            counter++;
            out += item.toString();
        }
        out += "}";
        return out;
    }

    public boolean contains(Set edit) {
        for (int i = 0; i < this.createset.length; i++)
        {
            if (compare(this.createset[i],edit))
            {
                return true;
            }
        }
        return false;
    }


    public boolean elementOf(Set edit) {

        for (int i = 0; i < edit.createset.length; i++)
        {
            if (compare(this,edit.createset[i]))
            {
                return true;
            }
        }
        return false;
    }

    public boolean subsetOf(Set edit)
    {
        for (int i = 0; i < this.createset.length; i++)
        {
            if (!this.createset[i].elementOf(edit))
            {
                return false;
            }
        }
        return true;
    }

    public boolean supersetOf(Set edit)
    {
        for (int i = 0; i < edit.createset.length; i++)
        {
            if(!edit.createset[i].elementOf(this))
            {
                return false;
            }
        }
        return true;
    }

    public int size()
    {
        return this.createset.length;
    }

    public boolean isEmpty()
    {
        if (this.createset.length == 0) {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean equals(Object newedit)
    {
        Set edit;
        if ( newedit!=null && newedit.getClass() == this.getClass() )
        {
            edit = (Set) newedit;
        }
        else
        {
            return false;
        }
        if( this.createset.length == edit.createset.length && this.supersetOf(edit) )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    static Set one = Set.newSet(empty);
    static Set two = Set.newSet(empty, one);
    static Set three = Set.newSet(empty, one, two);
    static  Set four = Set.newSet(empty, one, two, three);
    static Set five = Set.newSet(empty, one, two, three, four);
    static Set six = Set.newSet(empty, one, two, three, four, five);
    static Set seven = Set.newSet(empty, one, two, three, four, five, six);
    static Set eight = Set.newSet(empty, one, two, three, four, five, six, seven);
    static Set nine = Set.newSet(empty, one, two, three, four, five, six, seven, eight);
    static Set ten = Set.newSet(empty, one, two, three, four, five, six, seven, eight, nine);
    private static Set[] number = {empty, one, two, three, four, five, six, seven, eight, nine, ten};

    public static Set number(int a)
    {
        Set create;
        if (a <= 10) {
            create = number[a];
        }
        else
        {
            create = number[10];
            for (int i = 11; i <= a; i++)
            {
                Set createcopy = new Set(i);
                for ( int j = 0; j<i-1; j++)
                {
                    createcopy.createset[j] = create.createset[j];
                }
                createcopy.createset[i-1] = create;
                create = createcopy;
            }
        }
        return create;
    }

    public boolean isNumber()
    {
        Set number =  Set.number(this.createset.length);

        if( compare(number,this))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static Set parseSet(java.lang.String stringset)
    {
        int counter = 0;
        int bracket = 0;
        for ( int i = 1; i < stringset.length()-1; i++)
        {
            if (stringset.charAt(i) == '{') bracket++;
            if (stringset.charAt(i) == '}') bracket--;
            if (bracket == 0 && stringset.charAt(i) == '}' )
            {
                counter++;
            }
        }
        Set create = new Set(counter);
        String createset = "";
        int i = 1;
        for ( int j = 0; j< counter; j++)
        {
            createset = "";
            bracket = 0;
            while( i < stringset.length()-1)
            {
                if (stringset.charAt(i) == '{') bracket++;
                if (stringset.charAt(i) == '}') bracket--;
                createset += stringset.charAt(i);
                if (bracket == 0) break;
                i++;
            }
            i = i + 3;
            create.createset[j] = Set.parseSet(createset);
        }
        create = create.newSet(create);
        create = create.union(create);
        return create;
    }

    public static void main(String[] args) {
    

    }
}