package com.xz.netty.demo.dserializable.marshall;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * Created by xz on 2020/1/29.
 */
public class MarshallingCodeCFactory {
    public static MarshallingDecoder buildDecoder(){
        MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial") ;
        MarshallingConfiguration marshallingConfiguration = new MarshallingConfiguration() ;
        marshallingConfiguration.setVersion(5);
        UnmarshallerProvider unmarshallerProvider = new DefaultUnmarshallerProvider(marshallerFactory,marshallingConfiguration) ;
        return new MarshallingDecoder(unmarshallerProvider) ;
    }

    public static MarshallingEncoder buildEncoder(){
        MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial") ;
        MarshallingConfiguration marshallingConfiguration = new MarshallingConfiguration() ;
        marshallingConfiguration.setVersion(5);
        MarshallerProvider marshallerProvider = new DefaultMarshallerProvider(marshallerFactory,marshallingConfiguration) ;
        return new MarshallingEncoder(marshallerProvider) ;
    }
}
