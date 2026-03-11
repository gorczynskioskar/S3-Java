package model;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import pracownik.Pracownik;

public class Model {
	private HashMap<String, Pracownik> pracownicy = new HashMap<>();

	public boolean containsPesel(String pesel) {
		return pracownicy.containsKey(pesel);
	}

	public void addEmployee(Pracownik pracownik) {
		pracownicy.put(pracownik.getPesel(), pracownik);
	}

	public Pracownik getByPesel(String pesel) {
		return pracownicy.get(pesel);
	}

	public boolean removeByPesel(String pesel) {
		return pracownicy.remove(pesel) != null;
	}

	public List<Pracownik> allEmployees() {
		return new ArrayList<>(pracownicy.values());
	}

	// --- Kopia zapasowa ---

	public void save(String fileName, BackupCompression compression) throws IOException {
		BackupCompression c = compression == BackupCompression.AUTO ? detectByExtension(fileName) : compression;
		if (c == BackupCompression.ZIP) {
			saveZip(fileName);
		} else if (c == BackupCompression.GZIP) {
			saveGzip(fileName);
		} else {
			throw new IllegalArgumentException("Nieznana kompresja");
		}
	}

	public void restore(String fileName) throws IOException, ClassNotFoundException {
		BackupCompression c = detectByExtension(fileName);
		if (c == BackupCompression.ZIP) {
			restoreZip(fileName);
		} else if (c == BackupCompression.GZIP) {
			restoreGzip(fileName);
		} else {
			throw new IllegalArgumentException("Nieobsługiwane rozszerzenie pliku: " + fileName);
		}
	}

	private BackupCompression detectByExtension(String name) {
		String n = name.toLowerCase(Locale.ROOT);
		if (n.endsWith(".zip"))
			return BackupCompression.ZIP;
		if (n.endsWith(".gzip") || n.endsWith(".gz"))
			return BackupCompression.GZIP;
		return BackupCompression.AUTO;
	}

	private void saveZip(String file) throws IOException {
		try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file))) {
			zos.putNextEntry(new ZipEntry("employees.ser"));

			ObjectOutputStream oos = new ObjectOutputStream(zos);
			oos.writeObject(pracownicy);
			oos.flush();
			zos.closeEntry();
		}
	}

	private void restoreZip(String file) throws IOException, ClassNotFoundException {
		HashMap<String, pracownik.Pracownik> loaded;
		try (ZipInputStream zis = new ZipInputStream(new FileInputStream(file))) {
			ZipEntry e = zis.getNextEntry();
			if (e == null)
				throw new EOFException("Brak wpisów w ZIP");
			ObjectInputStream ois = new ObjectInputStream(zis);
			Object obj = ois.readObject();
			loaded = castMap(obj);
		}
		this.pracownicy = loaded;
	}

	private void saveGzip(String file) throws IOException {
		try (GZIPOutputStream gos = new GZIPOutputStream(new FileOutputStream(file));
				ObjectOutputStream oos = new ObjectOutputStream(gos)) {
			oos.writeObject(pracownicy);
		}
	}

	private void restoreGzip(String file) throws IOException, ClassNotFoundException {
		HashMap<String, pracownik.Pracownik> loaded;
		try (GZIPInputStream gis = new GZIPInputStream(new FileInputStream(file));
				ObjectInputStream ois = new ObjectInputStream(gis)) {
			Object obj = ois.readObject();
			loaded = castMap(obj);
		}
		this.pracownicy = loaded;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Pracownik> castMap(Object obj) throws StreamCorruptedException {
		if (!(obj instanceof HashMap))
			throw new StreamCorruptedException("Oczekiwano HashMap.");
		return (HashMap<String, Pracownik>) obj;
	}
}
