package com.business_website.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.business_website.models.PlacementDetails;

public interface ExcelService {
    ByteArrayInputStream placementDetailsToExcel(List<PlacementDetails> placementDetails) throws IOException;
}
