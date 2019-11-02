package public_transport_webservice;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.schildbach.pte.AbstractNetworkProvider;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public static AbstractNetworkProvider get_provider(String providerName, String authorization) {

        Object object = null;
        try {
            Class<?> classDefinition = Class.forName("de.schildbach.pte." + providerName + "Provider");

            if (authorization.isEmpty()) {
                object = classDefinition.newInstance();
            } else {
                Constructor<?> classConstructor = classDefinition.getConstructor(String.class);
                object = classConstructor.newInstance(new Object[] { authorization });
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    
        return (AbstractNetworkProvider) object;

    }

}