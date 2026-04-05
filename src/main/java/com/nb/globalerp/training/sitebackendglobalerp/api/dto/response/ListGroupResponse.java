package com.nb.globalerp.training.sitebackendglobalerp.api.dto.response;

import java.util.List;

public record ListGroupResponse(
        List<GroupResponse> groupResponses,
        List<IdPair> intersections
) {
}
