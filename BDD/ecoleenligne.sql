-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Mer 20 Mai 2020 à 00:19
-- Version du serveur :  5.6.17
-- Version de PHP :  5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `ecoleenligne`
--

-- --------------------------------------------------------

--
-- Structure de la table `contenu`
--

CREATE TABLE IF NOT EXISTS `contenu` (
  `idContenu` int(11) NOT NULL AUTO_INCREMENT,
  `idMatiere` int(11) NOT NULL,
  `nom` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `type` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `URL` varchar(2083) COLLATE utf8_unicode_ci NOT NULL,
  `typeURL` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`idContenu`),
  KEY `idMatiere` (`idMatiere`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=8 ;

--
-- Contenu de la table `contenu`
--

INSERT INTO `contenu` (`idContenu`, `idMatiere`, `nom`, `type`, `URL`, `typeURL`) VALUES
(1, 1, 'les départements de France métropolitaine', 'Exercice', 'https://static.ccm2.net/scrib-files/10597289.pdf', 'PDF'),
(2, 1, 'départements de l''outre-mer', 'Exercice', 'https://static.ccm2.net/scrib-files/10592017.pdf', 'PDF'),
(3, 2, 'Ecrire des phrases', 'Exercice', 'https://static.ccm2.net/scrib-files/10595051.pdf', 'PDF'),
(4, 3, 'Les additions', 'Exercice', 'https://static.ccm2.net/scrib-files/10595609.pdf', 'PDF'),
(5, 1, 'La prehistoire', 'Cours', 'https://youtu.be/t_DkbacH0pM', 'Video'),
(6, 3, 'Resoudre un probleme', 'Cours', 'https://youtu.be/n4_Yx5_RDsI', 'Video'),
(7, 2, 'Les determinant', 'Cours', 'https://youtu.be/bR1GNSIrVyM', 'Videos');

-- --------------------------------------------------------

--
-- Structure de la table `eleve`
--

CREATE TABLE IF NOT EXISTS `eleve` (
  `idEleve` int(11) NOT NULL,
  `idParent` int(11) NOT NULL,
  `lienparente` int(1) NOT NULL,
  `formule` int(10) NOT NULL,
  `formation` int(1) NOT NULL,
  PRIMARY KEY (`idEleve`),
  KEY `idParent` (`idParent`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `historique`
--

CREATE TABLE IF NOT EXISTS `historique` (
  `idHistorique` int(11) NOT NULL AUTO_INCREMENT,
  `idEleve` int(11) NOT NULL,
  `type` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `date` datetime NOT NULL,
  `description` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`idHistorique`),
  KEY `idEleve` (`idEleve`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `listematiere`
--

CREATE TABLE IF NOT EXISTS `listematiere` (
  `idUtilisateur` int(11) NOT NULL,
  `idMatiere` int(11) NOT NULL,
  `anneeScolaire` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `niveauScolaire` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  KEY `idMatiere` (`idMatiere`),
  KEY `idUtilisateur` (`idUtilisateur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `matiere`
--

CREATE TABLE IF NOT EXISTS `matiere` (
  `idMatiere` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `niveauScolaire` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`idMatiere`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=4 ;

--
-- Contenu de la table `matiere`
--

INSERT INTO `matiere` (`idMatiere`, `nom`, `niveauScolaire`) VALUES
(1, 'Histoire', 'CE2'),
(2, 'Francais', 'CE2'),
(3, 'Mathematique', 'CE2');

-- --------------------------------------------------------

--
-- Structure de la table `parent`
--

CREATE TABLE IF NOT EXISTS `parent` (
  `idParent` int(11) NOT NULL,
  PRIMARY KEY (`idParent`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `professeur`
--

CREATE TABLE IF NOT EXISTS `professeur` (
  `idProfesseur` int(11) NOT NULL,
  PRIMARY KEY (`idProfesseur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE IF NOT EXISTS `utilisateur` (
  `idUtilisateur` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `prenom` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `login` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `mdp` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `moyenpaiement` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`idUtilisateur`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=8 ;

--
-- Contenu de la table `utilisateur`
--

INSERT INTO `utilisateur` (`idUtilisateur`, `nom`, `prenom`, `email`, `login`, `mdp`, `moyenpaiement`) VALUES
(7, 'john', 'doe', 'in', 'your', '$2y$10$.HgzkrjhrRCmMiy1ZERqVeyeTrthEzCwQfYPykC/Jes', '55/666/33');

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `contenu`
--
ALTER TABLE `contenu`
  ADD CONSTRAINT `idMatiereFK` FOREIGN KEY (`idMatiere`) REFERENCES `matiere` (`idMatiere`);

--
-- Contraintes pour la table `eleve`
--
ALTER TABLE `eleve`
  ADD CONSTRAINT `idEleve` FOREIGN KEY (`idEleve`) REFERENCES `utilisateur` (`idUtilisateur`),
  ADD CONSTRAINT `idParent_FK` FOREIGN KEY (`idParent`) REFERENCES `parent` (`idParent`);

--
-- Contraintes pour la table `historique`
--
ALTER TABLE `historique`
  ADD CONSTRAINT `idEleve_FK` FOREIGN KEY (`idEleve`) REFERENCES `eleve` (`idEleve`);

--
-- Contraintes pour la table `listematiere`
--
ALTER TABLE `listematiere`
  ADD CONSTRAINT `idMatiere_FK` FOREIGN KEY (`idMatiere`) REFERENCES `matiere` (`idMatiere`),
  ADD CONSTRAINT `idUtilisateur_FK` FOREIGN KEY (`idUtilisateur`) REFERENCES `utilisateur` (`idUtilisateur`);

--
-- Contraintes pour la table `parent`
--
ALTER TABLE `parent`
  ADD CONSTRAINT `idParent` FOREIGN KEY (`idParent`) REFERENCES `utilisateur` (`idUtilisateur`);

--
-- Contraintes pour la table `professeur`
--
ALTER TABLE `professeur`
  ADD CONSTRAINT `idProfesseur` FOREIGN KEY (`idProfesseur`) REFERENCES `utilisateur` (`idUtilisateur`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
