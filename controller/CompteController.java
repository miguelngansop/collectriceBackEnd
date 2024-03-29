package com.sarki.micro.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sarki.micro.model.Agent;
import com.sarki.micro.model.Client;
import com.sarki.micro.model.Compte;
import com.sarki.micro.model.CompteCourant;
import com.sarki.micro.model.Retrait;
import com.sarki.micro.model.Versement;
import com.sarki.micro.repository.AgentRepository;
import com.sarki.micro.repository.ClientRepository;
import com.sarki.micro.repository.CompteRepository;
import com.sarki.micro.repository.OperationRepository;

import exception.ResourceNotFoundException;

@RestController
@RequestMapping("/apicompte")
public class CompteController {

	@Autowired
	CompteRepository compteRepo;

	@Autowired
	ClientRepository clientRepo;

	@Autowired
	OperationRepository opRepo;

	@Autowired
	AgentRepository agentRepo;

	// Create a new CompteCourant

	// Get All Comptes
	@GetMapping("/comptes")
	public List<Compte> getAllCompte() {
		return compteRepo.findAll();
	}
	
	// Get history Comptes
		@GetMapping("/historique/{id}")
		public List<?> getAllHis(@PathVariable(value="id") Long compteId){
			return null ;
		}

	@PostMapping("/comptecourant/{id}")
	public CompteCourant createCompteCourant(@PathVariable(value = "id") Long clientId) {
		CompteCourant cpt = new CompteCourant();
		if (clientRepo.existsById(clientId)) {
			cpt.setSolde(0); // initialisation du solde a la creation
			cpt.setCreatedAt(new Date());
			cpt.setDecouvert(0); // initialisation du decouvert a la creation
			cpt.setUpdatedAt(new Date());
			Client cl = new Client();
			cl.setId(clientId);
			cl.setEmail(clientRepo.getOne(clientId).getEmail());
			cl.setDateDeNaissance(clientRepo.getOne(clientId).getDateDeNaissance());
			cl.setNom(clientRepo.getOne(clientId).getNom());
			cl.setNomDeLaMere(clientRepo.getOne(clientId).getNomDeLaMere());
			cl.setNomDuPere(clientRepo.getOne(clientId).getNomDuPere());
			cl.setNumcni(clientRepo.getOne(clientId).getNumcni());
			cl.setProfession(clientRepo.getOne(clientId).getProfession());
			cl.setResidence(clientRepo.getOne(clientId).getResidence());
			cl.setTelephone(clientRepo.getOne(clientId).getTelephone());
			System.out.println("\n\n" + cl.getNom() + " " + cl.getId() + "\n\n");

			cpt.setClient(cl);

			return compteRepo.save(cpt);
		} else {
			return null;
		}

	}

	// Get a Single Compte
	@GetMapping("/compte/{id}")
	public Compte getCompteById(@PathVariable(value = "id") Long compteId) {
		return compteRepo.findById(compteId).orElseThrow(() -> new ResourceNotFoundException("Compte", "id", compteId));
	}

	// versement
	@PatchMapping("/compte/{idCompte}/{idAgent}")
	public ResponseEntity<?> versement(@PathVariable(value = "idCompte") Long compteId,
			@PathVariable(value = "idAgent") Long agentid, @Valid @RequestBody Versement details)
					throws ResourceNotFoundException {
		Compte compte = compteRepo.findById(compteId)
				.orElseThrow(() -> new ResourceNotFoundException("CompteCourant", "id", compteId));
		details.setSoldePrecedent(compte.getSolde());
		Agent a = new Agent();
		a.setId(agentid);
		details.setAgent(a);
		compte.setSolde(compte.getSolde() + details.getMontant());
		final Compte updatedCompte = compteRepo.save(compte);
		details.setCompte(compte);
		details.setCreatedAt(new Date());
		details.setUpdatedAt(new Date());
		opRepo.save(details);
		return new ResponseEntity<>(updatedCompte, HttpStatus.OK);
	}

	// retrait

	@PatchMapping("/compte/retrait/{idCompte}/{idAgent}")
	public ResponseEntity<?> retrait(@PathVariable(value = "idCompte") Long compteId,
			@PathVariable(value = "idAgent") Long agentId, @Valid @RequestBody Retrait details)
					throws ResourceNotFoundException {
		Compte compte = compteRepo.findById(compteId)
				.orElseThrow(() -> new ResourceNotFoundException("Compte", "id", compteId));
		if (compte.getSolde() >= details.getMontant()) {
			details.setSoldePrecedent(compte.getSolde());
			details.setAgent(agentRepo.getOne(agentId));
			compte.setSolde(compte.getSolde() - details.getMontant());
			final Compte updatedCompte = compteRepo.save(compte);
			details.setCompte(compte);
			details.setCreatedAt(new Date());
			details.setUpdatedAt(new Date());
			opRepo.save(details);
			return ResponseEntity.ok(updatedCompte);

		} else {
			return ResponseEntity.ok(compte);
		}

	}

}
