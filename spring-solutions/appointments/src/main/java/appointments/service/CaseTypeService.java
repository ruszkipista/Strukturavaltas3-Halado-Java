package appointments.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import appointments.model.CaseType;
import appointments.model.CaseTypeDto;
import appointments.model.CaseTypeMapper;

@Service
public class CaseTypeService {
    private CaseTypeMapper mapper;

    private final List<CaseType> types = List.of(
        CaseType.C001,
        CaseType.C002
    );

    public CaseTypeService(CaseTypeMapper mapper) {
        this.mapper = mapper;
    }

    public List<CaseTypeDto> getCaseTypes() throws IOException {
        return this.mapper.toDto(this.types);
    }

    public CaseType getCaseTypeById(String id) {
        return this.types.stream()
            .filter(t -> t.getId().equals(id))
            .findAny()
            .orElseThrow(()->new IllegalArgumentException("Invalid CaseType Id:" + id));
    }
}