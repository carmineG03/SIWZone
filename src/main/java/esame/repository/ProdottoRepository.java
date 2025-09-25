package esame.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import esame.model.Prodotto;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {
	public List<Prodotto> findByNome(String nome);

	public Long countById(Long id);
	public List<Prodotto> findByNomeContainingIgnoreCase(String nome);
	
	public List<Prodotto> findByTipologiaIgnoreCase(String tipologia);

	public Optional<Prodotto> findById(Long id);
	
	@Query("SELECT p FROM Prodotto p LEFT JOIN FETCH p.prodottiSimili WHERE p.id = :id")
	public Optional<Prodotto> findByIdWithProdottiSimili(@Param("id") Long id);

	@Query("SELECT p FROM Prodotto p " + " LEFT JOIN FETCH p.commenti "
			+ " WHERE p.id = :id")
	Optional<Prodotto> findByIdWithCommentiAndAutori(@Param("id") Long id);

	/**
	 * Carica un prodotto con i suoi commenti
	 * @param id del prodotto
	 * @return prodotto con commenti
	 */
	@Query("SELECT p FROM Prodotto p LEFT JOIN FETCH p.commenti WHERE p.id = :id")
	Optional<Prodotto> findByIdWithCommenti(@Param("id") Long id);
	
	/**
	 * Carica un prodotto con solo gli ultimi 10 commenti per ottimizzazione
	 * @param id del prodotto
	 * @return prodotto con commenti limitati
	 */
	@Query("SELECT p FROM Prodotto p WHERE p.id = :id")
	Optional<Prodotto> findByIdOptimized(@Param("id") Long id);

	/*@Query("SELECT p FROM Prodotto p ORDER BY p.prezzzo DESC")
	List<Prodotto> findTopProdottiByPrezzo();*/

	/*@Query("SELECT p FROM Prodotto p  JOIN p.commenti c GROUP BY p ORDER BY COUNT(c) DESC")
	List<Prodotto> findTopProdottiByCommenti();*/

	@Query("SELECT p FROM Prodotto p ORDER BY p.nome ASC")
	List<Prodotto> findAllOrderByNomeAsc();

	/**
	 * Restituisce tutti i nomi dei prodotti ordinati alfabeticamente per l'autocomplete
	 */
	@Query("SELECT DISTINCT p.nome FROM Prodotto p ORDER BY p.nome ASC")
	List<String> findAllNomiProdotti();

	/**
	 * Esempi di query personalizzate con @Query
	 *
	 * // PATTERN 1: Selezione con criteri
	 * @Query("SELECT e FROM {Entity} e WHERE e.{campo} {operatore} :parametro")
	 * List<{Entity}> find{Descrizione}(@Param("parametro") {Tipo} parametro);
	 *
	 * // PATTERN 2: Top N elementi
	 * @Query("SELECT e FROM {Entity} e ORDER BY e.{campo} {ASC/DESC}")
	 * List<{Entity}> findTop{N}By{Criterio}(Pageable pageable);
	 *
	 * // PATTERN 3: Con conteggio/aggregazione
	 * @Query("SELECT e FROM {Entity} e LEFT JOIN e.{relazione} r GROUP BY e ORDER BY COUNT(r) DESC")
	 * List<{Entity}> find{Descrizione}WithMost{Relazione}(Pageable pageable);
	 *
	 * // PATTERN 4: Query personalizzata
	 * @Query("SELECT e FROM {Entity} e JOIN e.{relazione} r WHERE {condizioni}")
	 * List<{Entity}> find{Descrizione}Custom();
	 */
}
