/**
 * Created by lukasz on 19.03.2017.
 */
public class Polygon {

    node first;
    int size;

    private class node {
         Vertex that;
         node next;
         node previous;

         private node(Vertex that, node next, node previous) {
             this.that = that;
             this.next = next;
             this.previous = previous;
         }
    }

    private node addPolygon(Vertex that, node edit) {

        node temp = new node(that,null,null);

        if( first.that  == null ) {
            temp.next = temp;
            temp.previous = temp;
            first = temp;
        }
        else {
            edit.previous.next = temp;
            temp.previous = edit.previous;
            temp.next = edit;
            edit.previous = temp;
        }
        this.size++;
        return temp;
    }

    private void removePolygon( node edit ) {

        if ( size == 1 ) {
            this.first.that = null;
            this.first.previous = null;
            this.first.next = null;
        }
        else {
            if( edit == first ) {
                first = first.next;
            }
            edit.previous.next = edit.next;
            edit.next.previous = edit.previous;
        }
        size--;
    }

    public Polygon ()
    {
        first = new node(null,null,null);
        size = 0 ;
    }

    public Polygon(Vertex[] vertices)
    {
        this();

        for ( int i = 0; i < vertices.length ; i++ ) {
            addPolygon(vertices[i],first);
        }
    }

    public int size(){
        return this.size;
    }

    public boolean isEmpty(){
        if( size == 0 ) return true;
        else return false;
    }

    public static class VertexIterator {

        Polygon edit;
        node index;
        node start;
        node returned;
        int counteredit;

        public VertexIterator(node firstvertex, Polygon edit) {
            this.index = firstvertex;
            setStart();
            this.counteredit = 0;
            this.edit = edit;
        }
        public Vertex next() {
            returned = index;
            index = index.next;
            counteredit++;
            return returned.that;
        }
        public Vertex previous() {
            index = index.previous;
            counteredit++;
            returned = index;
            return index.that;
        }
        public void setStart() {
            start = index;
            counteredit = 0;
        }
        public boolean looped() {
            if(counteredit != 0 && start == index ) {
                return true;
            }
            else return false;
        }
        public void add(Vertex v) {
            if ( edit.first.that == null ) {
                index =  edit.addPolygon(v,null);
                setStart();
                return;
            }
            edit.addPolygon(v,index);
        }
        public void set(Vertex v) {
            returned.that = v;
        }
        public void remove() {
            if( returned == index ) {
                index = index.next;
            }
            if( returned == start )
            {
                start = start.next;
            }
            edit.removePolygon(returned);
        }
    }

    public VertexIterator vertices() {
        VertexIterator index = new VertexIterator(this.first,this);
        return index;
    }

    public VertexIterator vertexAt(Vertex point)
    {
        VertexIterator index;
        node temp = this.first;
        node result = this.first;
        double length, lengthtemp;

        if( first.that == null ) {
            index =  new VertexIterator(this.first,this);
        }
        else {
            length = Math.pow(temp.that.x - point.x,2) + Math.pow(temp.that.y - point.y,2);
            for (int i = 1; i < size; i++) {
                temp = temp.next;
                lengthtemp = Math.pow(temp.that.x - point.x,2) + Math.pow(temp.that.y - point.y,2);
                if( length > lengthtemp)
                {
                    result = temp;
                    length = lengthtemp;
                }
            }
            index =  new VertexIterator(result,this);
        }
        return index;
    }

    public static class EdgeIterator {

        Edge index = new Edge(null, null);
        node setstart;
        node start;
        node end;
        int counter;

        private void setEdge(Vertex start, Vertex end) {
            index.start = start;
            index.end = end;
        }
        public EdgeIterator(node first, node next ) {
            start = first;
            end = next;
            setstart = start;
            counter = 0;
        }
        public Edge next() {
            setEdge(start.that,end.that);
            start = start.next;
            end = end.next;
            counter++;
            return index;
        }
        public Edge previous() {
            end = end.previous;
            start = start.previous;
            setEdge(end.that,start.that);
            counter++;
            return index;
        }
        public void setStart() {
            counter = 0;
            setstart = start;
        }
        public boolean looped() {
            if ( counter != 0 && start == setstart ) return true;
            else return false;
        }
    }

    EdgeIterator edges()
    {
        EdgeIterator index = new EdgeIterator(this.first,this.first.next);
        return index;
    }

    EdgeIterator edgeAt(Vertex point)
    {
        EdgeIterator index;
        node tempedge = this.first;
        node resultedge = this.first;
        Edge temp = new Edge(tempedge.that, tempedge.next.that);

        double length, lengthtemp;

        if( first.that == null ) {
            index =  new EdgeIterator(tempedge, tempedge.next);
        }
        else {

            length = temp.distanceTo(point);

            for (int i = 1; i < size; i++) {
                tempedge = tempedge.next;
                temp.start = tempedge.that;
                temp.end = tempedge.next.that;
                lengthtemp = temp.distanceTo(point);
                if( length > lengthtemp )
                {
                    resultedge = tempedge;
                    length = lengthtemp;
                }
            }
            index =  new EdgeIterator(resultedge, resultedge.next);
        }
        return index;
    }
}
