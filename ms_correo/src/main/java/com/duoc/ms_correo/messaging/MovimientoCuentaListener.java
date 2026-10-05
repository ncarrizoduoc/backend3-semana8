package com.duoc.ms_correo.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import tools.jackson.databind.ObjectMapper;

@Component 
public class MovimientoCuentaListener {

    Logger logger = LoggerFactory.getLogger(MovimientoCuentaListener.class);
    
    @JmsListener(destination = "movimientos_cuentas")
    public void recibir(String json){
        ObjectMapper mapper = new ObjectMapper();
        MovimientoCuentaEvent event = mapper.readValue(json, MovimientoCuentaEvent.class);
        logger.info("Se ha recibido evento con ID: " + event.getId());
        procesarEvento(event);
    }

    public void procesarEvento(MovimientoCuentaEvent event){
        logger.info("Procesando evento con ID: " + event.getId());
        logger.info("Se ha enviado el siguiente comprobante de transacción al correo del titular de la cuenta:");
        logger.info(event.toString());
    }
}
