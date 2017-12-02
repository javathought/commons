#language: fr

  Fonctionnalité: Calcul des jours fériés

    Scénario:
      Quand la date est '01/01/2017'
      Alors le jour n'est pas travaillé

    Plan du scénario:
      Quand la date est '<date>'
      Alors le jour n'est pas travaillé
      Exemples:
        |date|
        |06/05/2017|
        |07/05/2017|
        |01/01/2017|
        |01/05/2017|
        |08/05/2017|
        |14/07/2017|
        |15/08/2017|
        |01/11/2017|
        |11/11/2017|
        |25/12/2017|
        |01/01/2018|
#        dimanches de paques et déduit
        |07/04/1996|
        |12/04/2020|
        |16/04/2017|
#        lundi de paques
        |08/04/1996|
        |13/04/2020|
        |17/04/2017|
#        jeudi de l'ascencion
        |25/05/2017|
#        lundi de pentecôte
        |05/06/2017|

    Plan du scénario:
      Quand la date est '<date>'
      Alors le jour est travaillé
      Exemples:
        |date|
        |02/01/2017|
        |04/05/2017|
        |05/05/2017|

    Scénario:
      Etant donné l'intervalle entre '01/11/2017' et '03/11/2017'
      Et le nombre de jours ouvrés par mois est
        | mois | nb |
        | 11 | 2 |

    Scénario:
      Etant donné l'intervalle entre '31/10/2017' et '03/11/2017'
      Et le nombre de jours ouvrés par mois est
        | mois | nb |
        | 10   | 1 |
        | 11   | 2 |