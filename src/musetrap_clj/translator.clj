(ns musetrap-clj.translator
  (:gen-class))

(def ^:private dictionary 
  { 
   :recipes {
             :animal_warrior {
                              :name {
                                     :en "Animal Warrior"
                                     :es "Guerrero animal" } 
                              :description {
                                            :en "An animal warrior"
                                            :es "Un guerrero animal" }}
             :humanoid_creature {
                                 :name {
                                        :en "Humanoid Creature"
                                        :es "Criatura humanoide" }
                                 :description {
                                               :en "A humanoid creature"
                                               :es "Una criatura humanoide" }}} 
   :bundles {
             :animals {
                       :name {
                              :en "Animals"
                              :es "Animales" }}
             :creatures {
                         :name {
                                :en "Creatures"
                                :es "Criaturas" }}}
   :ingredients {
                 :dog {
                       :name {
                              :en "Dog"
                              :es "Perro" }}
                 :cat {
                       :name {
                              :en "Cat"
                              :es "Gato" }}
                 :bird {
                        :name {
                               :en "Bird"
                               :es "Pájaro" }}
                 :horse {
                         :name {
                                :en "Horse"
                                :es "Caballo" }}
                 :ork {
                       :name {
                              :en "Ork"
                              :es "Orco" }}
                 :werewolf {
                            :name {
                                   :en "Werewolf"
                                   :es "Hombre lobo" }}
                 :troll {
                         :name {
                                :en "Troll"
                                :es "Trol" }}
                 :dragon {
                          :name {
                                 :en "Dragon"
                                 :es "Dragón" }}
                 } 
   })

(defn translate
  [query default]
  (get-in dictionary query default))
