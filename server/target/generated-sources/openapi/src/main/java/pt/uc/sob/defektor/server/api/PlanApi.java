/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (4.3.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package pt.uc.sob.defektor.server.api;

import pt.uc.sob.defektor.server.model.Plan;
import java.util.UUID;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-07-14T01:05:44.936626131+01:00[Europe/Lisbon]")

@Validated
@Api(value = "plan", description = "the plan API")
public interface PlanApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * DELETE /plan : delete all plans
     * Delete all plan
     *
     * @return all plan deleted (status code 200)
     */
    @ApiOperation(value = "delete all plans", nickname = "allPlansDelete", notes = "Delete all plan", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "all plan deleted") })
    @RequestMapping(value = "/plan",
        method = RequestMethod.DELETE)
    default ResponseEntity<Void> allPlansDelete() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /plan : add plan
     * Add plan
     *
     * @param plan Added Plan (required)
     * @return plan created (status code 201)
     *         or invalid input, object invalid (status code 400)
     *         or plan already exists (status code 409)
     */
    @ApiOperation(value = "add plan", nickname = "planAdd", notes = "Add plan", response = Plan.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "plan created", response = Plan.class),
        @ApiResponse(code = 400, message = "invalid input, object invalid"),
        @ApiResponse(code = 409, message = "plan already exists") })
    @RequestMapping(value = "/plan",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<Plan> planAdd(@ApiParam(value = "Added Plan" ,required=true )  @Valid @RequestBody Plan plan) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"system\" : { \"name\" : \"kubernetes\" }, \"injektions\" : [ { \"workLoad\" : { \"duration\" : 120, \"image\" : { \"name\" : \"mangodb\", \"tag\" : \"latest\", \"user\" : \"sob\" }, \"slaves\" : 1, \"replicas\" : 1, \"cmd\" : \"sh shesellsshellsbytheseashore.sh\", \"env\" : [ { \"value\" : \"value\", \"key\" : \"key\" }, { \"value\" : \"value\", \"key\" : \"key\" } ] }, \"totalRuns\" : 0, \"ijk\" : { \"name\" : \"HoleyBoat\", \"params\" : [ { \"value\" : \"value\", \"key\" : \"key\" }, { \"value\" : \"value\", \"key\" : \"key\" } ] }, \"target\" : { \"name\" : \"istio_ingress_2314234h21345\", \"type\" : { \"name\" : \"container\" } } }, { \"workLoad\" : { \"duration\" : 120, \"image\" : { \"name\" : \"mangodb\", \"tag\" : \"latest\", \"user\" : \"sob\" }, \"slaves\" : 1, \"replicas\" : 1, \"cmd\" : \"sh shesellsshellsbytheseashore.sh\", \"env\" : [ { \"value\" : \"value\", \"key\" : \"key\" }, { \"value\" : \"value\", \"key\" : \"key\" } ] }, \"totalRuns\" : 0, \"ijk\" : { \"name\" : \"HoleyBoat\", \"params\" : [ { \"value\" : \"value\", \"key\" : \"key\" }, { \"value\" : \"value\", \"key\" : \"key\" } ] }, \"target\" : { \"name\" : \"istio_ingress_2314234h21345\", \"type\" : { \"name\" : \"container\" } } } ], \"name\" : \"Order 66\", \"id\" : \"d290f1ee-6c54-4b01-90e6-d701748f0851\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * DELETE /plan/{planId} : delete plan
     * Delete plan
     *
     * @param planId Plan identifier (required)
     * @return plan deleted (status code 200)
     *         or plan does not exist (status code 400)
     */
    @ApiOperation(value = "delete plan", nickname = "planDelete", notes = "Delete plan", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "plan deleted"),
        @ApiResponse(code = 400, message = "plan does not exist") })
    @RequestMapping(value = "/plan/{planId}",
        method = RequestMethod.DELETE)
    default ResponseEntity<Void> planDelete(@ApiParam(value = "Plan identifier",required=true) @PathVariable("planId") UUID planId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /plan/{planId} : plan info
     * Get plan information
     *
     * @param planId Plan identifier (required)
     * @return plan information (status code 200)
     *         or plan does not exist (status code 400)
     */
    @ApiOperation(value = "plan info", nickname = "planGet", notes = "Get plan information", response = Plan.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "plan information", response = Plan.class),
        @ApiResponse(code = 400, message = "plan does not exist") })
    @RequestMapping(value = "/plan/{planId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<Plan> planGet(@ApiParam(value = "Plan identifier",required=true) @PathVariable("planId") UUID planId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"system\" : { \"name\" : \"kubernetes\" }, \"injektions\" : [ { \"workLoad\" : { \"duration\" : 120, \"image\" : { \"name\" : \"mangodb\", \"tag\" : \"latest\", \"user\" : \"sob\" }, \"slaves\" : 1, \"replicas\" : 1, \"cmd\" : \"sh shesellsshellsbytheseashore.sh\", \"env\" : [ { \"value\" : \"value\", \"key\" : \"key\" }, { \"value\" : \"value\", \"key\" : \"key\" } ] }, \"totalRuns\" : 0, \"ijk\" : { \"name\" : \"HoleyBoat\", \"params\" : [ { \"value\" : \"value\", \"key\" : \"key\" }, { \"value\" : \"value\", \"key\" : \"key\" } ] }, \"target\" : { \"name\" : \"istio_ingress_2314234h21345\", \"type\" : { \"name\" : \"container\" } } }, { \"workLoad\" : { \"duration\" : 120, \"image\" : { \"name\" : \"mangodb\", \"tag\" : \"latest\", \"user\" : \"sob\" }, \"slaves\" : 1, \"replicas\" : 1, \"cmd\" : \"sh shesellsshellsbytheseashore.sh\", \"env\" : [ { \"value\" : \"value\", \"key\" : \"key\" }, { \"value\" : \"value\", \"key\" : \"key\" } ] }, \"totalRuns\" : 0, \"ijk\" : { \"name\" : \"HoleyBoat\", \"params\" : [ { \"value\" : \"value\", \"key\" : \"key\" }, { \"value\" : \"value\", \"key\" : \"key\" } ] }, \"target\" : { \"name\" : \"istio_ingress_2314234h21345\", \"type\" : { \"name\" : \"container\" } } } ], \"name\" : \"Order 66\", \"id\" : \"d290f1ee-6c54-4b01-90e6-d701748f0851\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /plan : list plans
     * List plans
     *
     * @return list of plans (status code 200)
     */
    @ApiOperation(value = "list plans", nickname = "planList", notes = "List plans", response = Plan.class, responseContainer = "List", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "list of plans", response = Plan.class, responseContainer = "List") })
    @RequestMapping(value = "/plan",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<List<Plan>> planList() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"system\" : { \"name\" : \"kubernetes\" }, \"injektions\" : [ { \"workLoad\" : { \"duration\" : 120, \"image\" : { \"name\" : \"mangodb\", \"tag\" : \"latest\", \"user\" : \"sob\" }, \"slaves\" : 1, \"replicas\" : 1, \"cmd\" : \"sh shesellsshellsbytheseashore.sh\", \"env\" : [ { \"value\" : \"value\", \"key\" : \"key\" }, { \"value\" : \"value\", \"key\" : \"key\" } ] }, \"totalRuns\" : 0, \"ijk\" : { \"name\" : \"HoleyBoat\", \"params\" : [ { \"value\" : \"value\", \"key\" : \"key\" }, { \"value\" : \"value\", \"key\" : \"key\" } ] }, \"target\" : { \"name\" : \"istio_ingress_2314234h21345\", \"type\" : { \"name\" : \"container\" } } }, { \"workLoad\" : { \"duration\" : 120, \"image\" : { \"name\" : \"mangodb\", \"tag\" : \"latest\", \"user\" : \"sob\" }, \"slaves\" : 1, \"replicas\" : 1, \"cmd\" : \"sh shesellsshellsbytheseashore.sh\", \"env\" : [ { \"value\" : \"value\", \"key\" : \"key\" }, { \"value\" : \"value\", \"key\" : \"key\" } ] }, \"totalRuns\" : 0, \"ijk\" : { \"name\" : \"HoleyBoat\", \"params\" : [ { \"value\" : \"value\", \"key\" : \"key\" }, { \"value\" : \"value\", \"key\" : \"key\" } ] }, \"target\" : { \"name\" : \"istio_ingress_2314234h21345\", \"type\" : { \"name\" : \"container\" } } } ], \"name\" : \"Order 66\", \"id\" : \"d290f1ee-6c54-4b01-90e6-d701748f0851\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /plan/validate : validate plan
     * Validate plan
     *
     * @param plan Validated Plan (required)
     * @return plan is valid (status code 200)
     *         or invalid input, object invalid (status code 400)
     */
    @ApiOperation(value = "validate plan", nickname = "planValidate", notes = "Validate plan", response = Plan.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "plan is valid", response = Plan.class),
        @ApiResponse(code = 400, message = "invalid input, object invalid") })
    @RequestMapping(value = "/plan/validate",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<Plan> planValidate(@ApiParam(value = "Validated Plan" ,required=true )  @Valid @RequestBody Plan plan) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"system\" : { \"name\" : \"kubernetes\" }, \"injektions\" : [ { \"workLoad\" : { \"duration\" : 120, \"image\" : { \"name\" : \"mangodb\", \"tag\" : \"latest\", \"user\" : \"sob\" }, \"slaves\" : 1, \"replicas\" : 1, \"cmd\" : \"sh shesellsshellsbytheseashore.sh\", \"env\" : [ { \"value\" : \"value\", \"key\" : \"key\" }, { \"value\" : \"value\", \"key\" : \"key\" } ] }, \"totalRuns\" : 0, \"ijk\" : { \"name\" : \"HoleyBoat\", \"params\" : [ { \"value\" : \"value\", \"key\" : \"key\" }, { \"value\" : \"value\", \"key\" : \"key\" } ] }, \"target\" : { \"name\" : \"istio_ingress_2314234h21345\", \"type\" : { \"name\" : \"container\" } } }, { \"workLoad\" : { \"duration\" : 120, \"image\" : { \"name\" : \"mangodb\", \"tag\" : \"latest\", \"user\" : \"sob\" }, \"slaves\" : 1, \"replicas\" : 1, \"cmd\" : \"sh shesellsshellsbytheseashore.sh\", \"env\" : [ { \"value\" : \"value\", \"key\" : \"key\" }, { \"value\" : \"value\", \"key\" : \"key\" } ] }, \"totalRuns\" : 0, \"ijk\" : { \"name\" : \"HoleyBoat\", \"params\" : [ { \"value\" : \"value\", \"key\" : \"key\" }, { \"value\" : \"value\", \"key\" : \"key\" } ] }, \"target\" : { \"name\" : \"istio_ingress_2314234h21345\", \"type\" : { \"name\" : \"container\" } } } ], \"name\" : \"Order 66\", \"id\" : \"d290f1ee-6c54-4b01-90e6-d701748f0851\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
