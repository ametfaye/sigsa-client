package sn.awi.pgca.business.service.mock;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import sn.awi.pgca.business.service.IListeReferentiel;
@Named("listeReferentielMock")
public class ListeReferentielMock implements IListeReferentiel {

	@Override
	public List<String> getListeTitreMandataire() {
		List<String> listeTitreMandataire = new ArrayList<String>();
		listeTitreMandataire.add("Avocat");
		listeTitreMandataire.add("Notaire");
		listeTitreMandataire.add("Huissier");
		listeTitreMandataire.add("Syndic");
		listeTitreMandataire.add("Auxiliaire de justice");
		return listeTitreMandataire;
	}

	@Override
	public List<String> getListeCivilite() {
		List<String> listeCivilite = new ArrayList<String>();
		listeCivilite.add("Mr");
		listeCivilite.add("Mme");
		listeCivilite.add("Mlle");
		return listeCivilite;
	}

	@Override
	public List<String> getListeBanqueOFSenegal() {

		List<String> listBanque = new ArrayList<String>();
		listBanque.add("AWB");
		listBanque.add("SGBS");
		listBanque.add("CITIBANK");
		listBanque.add("BICIS");
		listBanque.add("CBAO");
		listBanque.add("BHS");
		listBanque.add("ICB");
		listBanque.add("BRS");
		listBanque.add("BOA");
		listBanque.add("BIS");
		listBanque.add("BAS");
		listBanque.add("BIMAO");
		listBanque.add("BSIC");
		listBanque.add("LOCAFRIQUE");
		listBanque.add("SOCRES");
		listBanque.add("CLS");
		listBanque.add("Ecobank");
		return listBanque;
	}

}
