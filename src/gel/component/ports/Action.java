package gel.component.ports;

public abstract class Action {
	protected abstract void run(String _data); 
	protected abstract void run(int _data); 
	protected abstract void run(long _data); 
	protected abstract void run(char _data); 
	protected abstract void run(char[] _data); 
	protected abstract void run(short _data); 
	protected abstract void run(byte[] _data); 
	protected abstract void run(float _data);
	protected abstract void run(double _data);
	
}
