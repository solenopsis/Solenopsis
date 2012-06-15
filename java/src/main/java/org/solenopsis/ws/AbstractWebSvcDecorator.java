package org.solenopsis.ws;

/**
 *
 * The purpose of this class is
 *
 * @author sfloess
 *
 */
public abstract class AbstractWebSvcDecorator<P> implements WebSvcDecorator<P> {
    private final WebSvc<P> decoratee;
    
    protected AbstractWebSvcDecorator(final WebSvc<P> decoratee) {
        this.decoratee = decoratee;
    }

    @Override
    public WebSvc<P> getDecoratee() {
        return decoratee;
    }

    @Override
    public SecurityWebSvc getSecurityWebSvc() {
        return getDecoratee().getSecurityWebSvc();
    }
}
