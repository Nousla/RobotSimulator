package robotsimulator.common;

public class Source<E> {
    private Listener<E> listener;
    
    public void setListener(Listener<E> listener) {
        this.listener = listener;
    }
    
    public void update(E element) {
        if(listener != null) {
            listener.onChange(element);
        }
    }
}
