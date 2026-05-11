package dev.auris.organization.application.query.list_organizations;

import dev.auris.organization.domain.port.out.LoadOrganizationPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListOrganizationsHandler {
    private final LoadOrganizationPort loadOrganizationPort;

    public ListOrganizationsHandler(LoadOrganizationPort loadOrganizationPort) {
        this.loadOrganizationPort = loadOrganizationPort;
    }

    public List<ListOrganizationsItem> handle(ListOrganizationsQuery query) {
        return loadOrganizationPort.loadAll()
                .stream()
                .map(ListOrganizationsItem::from)
                .toList();
    }
}
