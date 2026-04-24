package com.capgemini.training.appointmentbooking.service.impl;

import com.capgemini.training.appointmentbooking.logic.FindTreatmentUc;
import com.capgemini.training.appointmentbooking.common.to.TreatmentCto;
import com.capgemini.training.appointmentbooking.logic.ManageTreatmentUc;
import com.capgemini.training.appointmentbooking.logic.TreatmentCreationTo;
import com.capgemini.training.appointmentbooking.service.api.TreatmentsApi;
import com.capgemini.training.appointmentbooking.service.mapper.TreatmentApiMapper;
import com.capgemini.training.appointmentbooking.service.model.Treatment;
import com.capgemini.training.appointmentbooking.service.model.TreatmentDetails;
import com.capgemini.training.appointmentbooking.service.model.TreatmentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TreatmentsApiController implements TreatmentsApi {

	private final FindTreatmentUc findTreatmentUc;
	private final ManageTreatmentUc manageTreatmentUc;
	private final TreatmentApiMapper treatmentMapper;

	@Override
	public ResponseEntity<Treatment> createTreatment(@Valid TreatmentRequest treatmentRequest) {

		TreatmentCreationTo creationTo = treatmentMapper.toCreationTo(treatmentRequest);
		TreatmentCto created = manageTreatmentUc.createTreatment(creationTo);
		Treatment response = treatmentMapper.toApiTreatment(created);

		return ResponseEntity.status(HttpStatus.CREATED).body(treatmentMapper.toApiTreatment(created));
	}

	@Override
	public ResponseEntity<TreatmentDetails> getTreatmentDetails(String treatmentId) {

		Long id = Long.valueOf(treatmentId);

		Optional<TreatmentCto> treatmentCto = findTreatmentUc.findById(id);

		if (treatmentCto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		TreatmentDetails response = treatmentMapper.toApiTreatmentDetails(treatmentCto.get());

		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<List<Treatment>> getTreatments() {

		List<Treatment> treatments = findTreatmentUc.findAll().stream().map(treatmentMapper::toApiTreatment).toList();

		return ResponseEntity.ok(treatments);

	}

}