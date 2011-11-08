package pl.edu.mimuw.ag291541.tvworld.service;

/**
 * This class is intended to obtain an object implementing
 * <code>TvWorldService</code> interface. Note that this is a singleton.
 * 
 * @author adas
 * 
 */
public class TvWorldServiceFactory {
	private static TvWorldServiceFactory instance = new TvWorldServiceFactory();

	private TvWorldServiceFactory() {
	}

	public static TvWorldServiceFactory getInstance() {
		return instance;
	}

	public TvWorldService getService() {
		return TvWorldServiceImpl.getInstance();
	}
}
