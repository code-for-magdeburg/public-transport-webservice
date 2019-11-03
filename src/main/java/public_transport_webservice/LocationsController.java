package public_transport_webservice;

import java.io.IOException;
import java.util.Date;
import java.util.EnumSet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.schildbach.pte.AbstractNetworkProvider;
import de.schildbach.pte.dto.Location;
import de.schildbach.pte.dto.LocationType;
import de.schildbach.pte.dto.NearbyLocationsResult;
import de.schildbach.pte.dto.Point;
import de.schildbach.pte.dto.QueryDeparturesResult;
import de.schildbach.pte.dto.SuggestLocationsResult;
import io.github.cdimascio.dotenv.Dotenv;

@RestController
public class LocationsController {

    private String providerName;
    private String authorization;

    private AbstractNetworkProvider provider = null;

     public LocationsController() {

        Dotenv dotenv = Dotenv
            .configure()
            .ignoreIfMissing()
            .load();

        providerName = dotenv.get("PROVIDER_NAME");
        authorization = dotenv.get("PROVIDER_AUTHORIZATION");

        provider = Application.get_provider(providerName, authorization);

    }

    @RequestMapping(value = "/query-nearby-locations", method = RequestMethod.GET)
    public NearbyLocationsResult nearby(
            @RequestParam(value = "types", defaultValue = "ANY") final EnumSet<LocationType> types,
            @RequestParam(value = "latitude") final double lat,
            @RequestParam(value = "longitude") final double lng,
            @RequestParam(value = "maxDistance", defaultValue = "500") final int maxDistance,
            @RequestParam(value = "maxLocations", defaultValue = "5") final int maxLocations)
            throws IOException {
        final Location location = Location.coord(Point.fromDouble(lat, lng));
        return provider.queryNearbyLocations(types, location, maxDistance, maxLocations);            
    }

    @RequestMapping(value = "/query-departures", method = RequestMethod.GET)
    public QueryDeparturesResult departures(
            @RequestParam(value = "stationId") final String stationId,
            @RequestParam(value = "maxDepartures", defaultValue = "20") final int maxDepartures,
            @RequestParam(value = "equivs", defaultValue = "0") final Boolean equivs)
            throws IOException {
        return provider.queryDepartures(stationId, new Date(), maxDepartures, equivs);
    }

    @RequestMapping(value = "/suggest-locations", method = RequestMethod.GET)
    public SuggestLocationsResult suggest(
            @RequestParam(value = "constraint") final CharSequence constraint,
            @RequestParam(value = "types", defaultValue = "ANY") final EnumSet<LocationType> types,
            @RequestParam(value = "maxLocations", defaultValue = "20") final int maxLocations)
            throws IOException {
        return provider.suggestLocations(constraint, types, maxLocations);
    }
    
}