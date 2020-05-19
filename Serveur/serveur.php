<<?php

$login = "root";
$mdp = "";
$bd = "ecoleenligne";
$serveur = "localhost";

//$con = mysqli_connect($serveur,$login,$mdp,$bd);
$db = new PDO("mysql:host=$serveur;dbname=$bd",$login,$mdp);

function randomString() {
    $alphabet = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890';
    $pass = array();
    $alphaLength = strlen($alphabet) - 1;
    for ($i = 0; $i < 8; $i++) {
        $n = rand(0, $alphaLength);
        $pass[$i] = $alphabet[$n];
    }
    return implode($pass);
}

function sendMail($to, $peudo, $mdp){
$subject = 'Inscription EcoleEnLigne';
$message = "Merci de votre Inscription : Pseudo:'$peudo' Mot de Passe:'$mdp'";
$headers = 'From: myemail@gmail.com' . "\r\n" .
    'X-Mailer: PHP/' . phpversion();

mail($to, $subject, $message, $headers);
}

if ($_POST["operation"] = "enregparent") {
  $nom = $_POST["nom"];
  $prenom = $_POST["prenom"];
  $email = $_POST["email"];
  $login = $_POST["login"];
  $mdp = $_POST["mdp"];
  $paie = $_POST["moyenpaie"];
  $mdp = password_hash($mdp, PASSWORD_BCRYPT);
  $nbC = (int) $_POST["nbChild"];
  $formu = (int) $_POST["formule"];
  $forma = (int) $_POST["formation"];
  $annee = $_POST["annee"];
  $idParent = "";
  $bool = false;

  //verifie la presence du pseudo ds la bdd
  $Sql_Query = $db->prepare("select idUtilisateur from utilisateur where login = '$login'");
  $Sql_Query->execute();
  $row = $Sql_Query->fetch();
  if($row){
    echo "Error:Le login est deja pris.";
    $bool = true;
  }
  //idem pour l'email
  $Sql_Query = $db->prepare("select idUtilisateur from utilisateur where email = '$email'");
  $Sql_Query->execute();
  $row = $Sql_Query->fetch();
  if($row){
    echo "Error:Votre email est deja utilisé.";
    $bool = true;
  }

  if($bool != true){
    //insertion du parent ds bdd
    $Sql_Query = $db->prepare("insert into utilisateur (nom, prenom, email, login, mdp, moyenpaiement) values ('$nom', '$prenom', '$email', '$login', '$mdp', '$paie')");
    $Sql_Query->execute();
    $Sql_Query = $db->prepare("select idUtilisateur from utilisateur where login = '$login'");
    $Sql_Query->execute();
    $row = $Sql_Query->fetch(PDO::FETCH_ASSOC);
    $idParent = (int) $row["idUtilisateur"];
    $Sql_Query = $db->prepare("insert into parent (idParent) values ('$idParent')");
    $Sql_Query->execute();

    //boucle sur le nombre d'enfant
    for ($i=0; $i < $nbC; $i++) {
      $s1 = "nom".$i;
      $s2 = "prenom".$i;
      $s3 = "lien".$i;
      $s4 = "niveau".$i;
      $nom = $_POST[$s1];
      $prenom = $_POST[$s2];
      $lien = (int) $_POST[$s3];
      $niveau = $_POST[$s4];

      $login = "";
      $mdp = "";
      $mdp = randomString();
      $bool = false;
      //genere un login et mot de passe pour l'enfant
      while(!$bool){
        $login = randomString();
        //verifie la presence du pseudo ds la bdd
        $Sql_Query = $db->prepare("select idUtilisateur from utilisateur where login = '$login'");
        $Sql_Query->execute();
        $row = $Sql_Query->fetch();
        if(empty($row)){
          $bool = true;
        }
      }
      //envoie du mail avec le login et mdp
      //sendMail($email,$login,$mdp)
      $mdp = password_hash($mdp, PASSWORD_BCRYPT);
      //insertion de l'enfant n°i
      $Sql_Query = $db->prepare("insert into utilisateur (nom, prenom, email, login, mdp, moyenpaiement) values ('$nom', '$prenom', '', '$login', '$mdp', '')");
      $Sql_Query->execute();
      $Sql_Query = $db->prepare("select idUtilisateur from utilisateur where login = '$login'");
      $Sql_Query->execute();
      $row = $Sql_Query->fetch(PDO::FETCH_ASSOC);
      $id = (int) $row["idUtilisateur"];
      $Sql_Query = $db->prepare("insert into eleve (idEleve, idParent, lienparente, formule, formation) values ('$id', '$idParent', '$lien', '$formu', '$forma')");
      $Sql_Query->execute();
      //ajout d'une liste de matiere pour l'eleve dont le nombre dependra de la formule choisi
      $Sql_Query = $db->prepare("select idMatiere from matiere where niveauScolaire = '$niveau'");
      $Sql_Query->execute();
      $row1 = $Sql_Query->fetch(PDO::FETCH_ASSOC);
      for ($i=0; $i < $formu; $i++) {
        $idM = (int) $row1["idMatiere"];
        $Sql_Q1 = $db->prepare("insert into listematiere (idUtilisateur, idMatiere, anneeScolaire, niveauScolaire) values ('$id', '$idM', '$annee', '$niveau')");
        $Sql_Q1->execute();
      }
    }
    echo "SUCCES";
  }

}elseif ($_POST["operation"] = "enregeleve") {
  $nom = $_POST["nom"];
  $prenom = $_POST["prenom"];
  $email = $_POST["email"];
  $login = $_POST["login"];
  $mdp = $_POST["mdp"];
  $paie = $_POST["moyenpaie"];
  $mdp = password_hash($mdp, PASSWORD_BCRYPT);
  $formu = (int) $_POST["formule"];
  $forma = (int) $_POST["formation"];
  $annee = $_POST["annee"];
  $niveau = $_POST["niveau"];
  $bool = false;

  //verifie la presence du pseudo ds la bdd
  $Sql_Query = $db->prepare("select idUtilisateur from utilisateur where login = '$login'");
  $Sql_Query->execute();
  $row = $Sql_Query->fetch();
  if($row){
    echo "Error:Le login est deja pris.";
    $bool = true;
  }
  //idem pour l'email
  $Sql_Query = $db->prepare("select idUtilisateur from utilisateur where email = '$email'");
  $Sql_Query->execute();
  $row = $Sql_Query->fetch();
  if($row){
    echo "Error:Votre email est deja utilisé.";
    $bool = true;
  }

  if($bool != true){
    //insertion de l'eleve ds bdd
    $Sql_Query = $db->prepare("insert into utilisateur (nom, prenom, email, login, mdp, moyenpaiement) values ('$nom', '$prenom', '$email', '$login', '$mdp', '$paie')");
    $Sql_Query->execute();
    $Sql_Query = $db->prepare("select idUtilisateur from utilisateur where login = '$login'");
    $Sql_Query->execute();
    $row = $Sql_Query->fetch(PDO::FETCH_ASSOC);
    $idEleve = (int) $row["idUtilisateur"];
    $Sql_Query = $db->prepare("insert into eleve (idEleve, idParent, lienparente, formule, formation) values ('$idEleve', '', '', '$formule', '$formation')");
    $Sql_Query->execute();

    //ajout d'une liste de matiere pour l'eleve dont le nombre dependra de la formule choisi
    $Sql_Query = $db->prepare("select idMatiere from matiere where niveauScolaire = '$niveau'");
    $Sql_Query->execute();
    $row1 = $Sql_Query->fetch(PDO::FETCH_ASSOC);
    for ($i=0; $i < $formu; $i++) {
      $idM = (int) $row1["idMatiere"];
      $Sql_Q1 = $db->prepare("insert into listematiere (idUtilisateur, idMatiere, anneeScolaire, niveauScolaire) values ('$idEleve', '$idM', '$annee', '$niveau')");
      $Sql_Q1->execute();
    }

    echo "SUCCES";
  }
}elseif ($_POST["operation"] = "enregprof") {
  $nom = $_POST["nom"];
  $prenom = $_POST["prenom"];
  $email = $_POST["email"];
  $login = $_POST["login"];
  $mdp = $_POST["mdp"];
  $mdp = password_hash($mdp, PASSWORD_BCRYPT);
  $annee = $_POST["annee"];
  $niveau = $_POST["niveau"];
  $bool = false;

  //verifie la presence du pseudo ds la bdd
  $Sql_Query = $db->prepare("select idUtilisateur from utilisateur where login = '$login'");
  $Sql_Query->execute();
  $row = $Sql_Query->fetch();
  if($row){
    echo "Error:Le login est deja pris.";
    $bool = true;
  }
  //idem pour l'email
  $Sql_Query = $db->prepare("select idUtilisateur from utilisateur where email = '$email'");
  $Sql_Query->execute();
  $row = $Sql_Query->fetch();
  if($row){
    echo "Error:Votre email est deja utilisé.";
    $bool = true;
  }

  if($bool != true){
    //insertion de l'eleve ds bdd
    $Sql_Query = $db->prepare("insert into utilisateur (nom, prenom, email, login, mdp, moyenpaiement) values ('$nom', '$prenom', '$email', '$login', '$mdp', '')");
    $Sql_Query->execute();
    $Sql_Query = $db->prepare("select idUtilisateur from utilisateur where login = '$login'");
    $Sql_Query->execute();
    $row = $Sql_Query->fetch(PDO::FETCH_ASSOC);
    $idProf = (int) $row["idUtilisateur"];
    $Sql_Query = $db->prepare("insert into professeur (idProfesseur) values ('$idProf')");
    $Sql_Query->execute();

    //ajout d'une liste de matiere au professeur
    $Sql_Query = $db->prepare("select idMatiere from matiere where niveauScolaire = '$niveau'");
    $Sql_Query->execute();
    $row1 = $Sql_Query->fetch(PDO::FETCH_ASSOC);
    for ($i=0; $i < $formu; $i++) {
      $idM = (int) $row1["idMatiere"];
      $Sql_Q1 = $db->prepare("insert into listematiere (idUtilisateur, idMatiere, anneeScolaire, niveauScolaire) values ('$idProf', '$idM', '$annee', '$niveau')");
      $Sql_Q1->execute();
    }

    echo "SUCCES";
  }
}elseif($_POST["operation"] = "connexion"){
  $login = $_POST["login"];
  $mdp = $_POST["mdp"];

  $Sql_Query = $db->prepare("select * from utilisateur where login = '$login'");
  $Sql_Query->execute();
  $row = $Sql_Query->fetch();
  if ($row) {
    if (password_verify($mdp, $row->mdp)) {
      //type de compte
      $type = "";
      $Sql_Query = $db->prepare("select idParent from parent where idParent = '$row->idUtilisateur'");
      $Sql_Query->execute();
      $row1 = $Sql_Query->fetch();
      if($row1){
        $type = "parent";
      }else{
        $Sql_Query = $db->prepare("select idEleve from eleve where idEleve = '$row->idUtilisateur'");
        $Sql_Query->execute();
        $row2 = $Sql_Query->fetch();
        if($row2){
          $type = "eleve";
          //ajout d'un historique si l'eleve se connecte
          $typ = "connexion";
          $date = NOW();
          $desc = "connexion de l'utilisateur";
          $Sql_Q1 = $db->prepare("insert into historique (idEleve, type, date, description) values ('$row->idUtilisateur', '$typ', '$date', '$desc')");
          $Sql_Q1->execute();
        }else {
          $Sql_Query = $db->prepare("select idProfesseur from professeur where idProfesseur = '$row->idUtilisateur'");
          $Sql_Query->execute();
          $row3 = $Sql_Query->fetch();
          if($row3){
            $type = "prof";
          }
        }
      }
      echo "SUCCES:".$type."/".$row->idUtilisateur."/".$row->nom."/".$row->prenom."/".$row->email."/".$row->login;
    }else {
      echo "Error: Pseudo ou Mot de passe Incorrect";
    }
  }
}elseif($_POST["operation"] = "getCours"){
  $id = $_POST["id"];
  $result = "";

  $Sql_Query = $db->prepare("select idMatiere from listematiere where idUtilisateur = '$id'");
  $Sql_Query->execute();
  $row = $Sql_Query->fetch();
  while ($row) {
      //type de compte
      $Sql_Query2 = $db->prepare("select nom,URL from contenu where idMatiere = '$row->idMatiere'");
      $Sql_Query2->execute();
      $row1 = $Sql_Query2->fetch();
      if($row1){
        $result = $result.$row1->nom."-".$row1->URL."/";
      }
      $row = $Sql_Query->fetch();
  }
  echo $result;
}

 ?>
