import PACK.domain.Xxx;
import PACK.repository.XxxRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Controller for interacting with Xxx resources.
 */
@RestController
public class XxxController
{
    /// The route used for operating on the Xxx collection.
    static final String ROUTE_COLLECTIVE = "/xxxs";

    /// The route used for operating on individual Xxx resources.
    static final String ROUTE_SINGLE = "/xxxs/{id}";

    /// Controller-specific logger.
    private static final Logger LOG = LoggerFactory.getLogger(XxxController.class);

    /// Xxx resources are stored and retrieved from this repository.
    private final XxxRepository xxxRepository;

    /**
     * The constructor called by Spring to inject dependencies into the controller.
     */
    protected XxxController(final XxxRepository xxxRepository)
    {
        this.xxxRepository = xxxRepository;
    }

    /**
     * Route that GETs many Xxx resources.  Supports paging and sorting.
     */
    @RequestMapping(path = ROUTE_COLLECTIVE, method = RequestMethod.GET)
    public Page<Xxx> getMany(final Pageable pageable)
    {
        return addLinks(xxxRepository.findAll(pageable));
    }

    /**
     * Route that GETs a single Xxx resource.
     *
     * @param id the id of the Xxx.
     */
    @RequestMapping(path = ROUTE_SINGLE, method = RequestMethod.GET)
    public HttpEntity<Xxx> getOne(@PathVariable final UUID id)
    {
        final Xxx xxx = xxxRepository.findOne(id);
        if (xxx == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<>(addLinks(xxx), HttpStatus.OK);
        }
    }

    /**
     * Route that DELETEs a single Xxx resource.
     *
     * @param id the id of the Xxx.
     */
    @RequestMapping(path = ROUTE_SINGLE, method = RequestMethod.DELETE)
    public HttpEntity<Void> deleteOne(@PathVariable final UUID id)
    {
        try
        {
            xxxRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (EmptyResultDataAccessException ex)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = ROUTE_SINGLE, method = RequestMethod.PUT)
    public HttpEntity<Xxx> updateOne(@RequestBody final Xxx resource)
    {
        final Xxx xxx = xxxRepository.save(resource);
        return new ResponseEntity<>(addLinks(xxx), HttpStatus.OK);
    }

    @RequestMapping(path = ROUTE_COLLECTIVE, method = RequestMethod.POST)
    public HttpEntity<Xxx> createOne(@RequestBody final Xxx resource) throws URISyntaxException
    {
        final Xxx xxx = xxxRepository.save(resource);
        return new ResponseEntity<>(addLinks(xxx), getCreatedHeaders(xxx), HttpStatus.CREATED);
    }

    private Page<Xxx> addLinks(final Page<Xxx> xxxPage)
    {
        return xxxPage.map(this::addLinks);
    }

    private Xxx addLinks(final Xxx xxx)
    {
        xxx.add(linkTo(methodOn(XxxController.class).getOne(xxx.getUuid())).withSelfRel());
        return xxx;
    }

    private HttpHeaders getCreatedHeaders(final Xxx xxx) throws URISyntaxException
    {
        final HttpHeaders responseHeaders = new HttpHeaders();
        final Link location = linkTo(methodOn(XxxController.class).getOne(xxx.getUuid())).withSelfRel();
        responseHeaders.setLocation(new URI(location.getHref()));
        return responseHeaders;
    }
}

