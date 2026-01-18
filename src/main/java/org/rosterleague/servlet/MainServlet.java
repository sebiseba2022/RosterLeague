package org.rosterleague.servlet;

import java.io.*;
import java.util.List;
import jakarta.inject.Inject;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.rosterleague.common.*;
import org.rosterleague.entities.Game;

@WebServlet(name = "mainServlet", value = "/")
public class MainServlet extends HttpServlet {
    @Inject
    Request ejbRequest;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        ejbRequest.clearAllEntities();
        insertInfo();

        // 2. Setam tipul continutului la HTML
        response.setContentType("text/html;charset=UTF-8");

        // 3. Generam pagina HTML cu Bootstrap
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
        out.println("<title>Roster League Dashboard</title>");
        // Bootstrap CDN
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("</head><body class='bg-light'>");

        out.println("<div class='container py-5'>");
        out.println("<h1 class='display-5 fw-bold text-center mb-5'>Roster League Management</h1>");

        // --- CERINTA 1: CLASAMENT (League Standings) ---
        // Afisam clasamentul pentru Liga 2
        renderStandings(out, "L2", "Hermanstadt Basketball League");

        // --- CERINTA 2: MECIURILE UNEI ECHIPE (Team Games) ---
        // Afisam meciurile echipei T1
        renderTeamGames(out, "T1", "Honey Bees Visalia");

        out.println("</div>"); // End container
        out.println("</body></html>");
    }

    private void renderStandings(PrintWriter out, String leagueId, String leagueName) {
        out.println("<div class='card shadow-sm mb-5'>");
        out.println("<div class='card-header bg-primary text-white'>");
        out.println("<h4 class='my-0'>Clasament: " + leagueName + " (" + leagueId + ")</h4>");
        out.println("</div>");
        out.println("<div class='card-body'>");

        out.println("<table class='table table-striped table-hover align-middle'>");
        out.println("<thead class='table-dark'><tr><th>#</th><th>Echipa</th><th class='text-center'>Meciuri Jucate</th><th class='text-center'>Puncte</th></tr></thead>");
        out.println("<tbody>");

        List<TeamStats> standings = ejbRequest.getLeagueStandings(leagueId);

        if (standings.isEmpty()) {
            out.println("<tr><td colspan='4' class='text-center'>Nu exista date pentru aceasta liga.</td></tr>");
        } else {
            int rank = 1;
            for (TeamStats team : standings) {
                String rowClass = (rank <= 3) ? "table-success" : ""; // Evidentiem Top 3
                out.println("<tr class='" + rowClass + "'>");
                out.println("<td>" + (rank++) + "</td>");
                out.println("<td class='fw-bold'>" + team.getTeamName() + "</td>");
                out.println("<td class='text-center'>" + team.getPlayed() + "</td>");
                out.println("<td class='text-center fs-5'><strong>" + team.getPoints() + "</strong></td>");
                out.println("</tr>");
            }
        }
        out.println("</tbody></table>");
        out.println("</div></div>");
    }

    private void renderTeamGames(PrintWriter out, String teamId, String teamName) {
        out.println("<div class='card shadow-sm'>");
        out.println("<div class='card-header bg-success text-white'>");
        out.println("<h4 class='my-0'>Istoric Meciuri: " + teamName + "</h4>");
        out.println("</div>");
        out.println("<div class='card-body'>");

        out.println("<table class='table table-bordered table-hover'>");
        out.println("<thead class='table-light'><tr><th class='w-40'>Gazda</th><th class='text-center w-20'>Scor</th><th class='w-40 text-end'>Oaspete</th></tr></thead>");
        out.println("<tbody>");

        List<Game> games = ejbRequest.getTeamGames(teamId);
        if (games.isEmpty()) {
            out.println("<tr><td colspan='3' class='text-center text-muted'>Niciun meci jucat.</td></tr>");
        } else {
            for (Game g : games) {
                out.println("<tr>");
                out.println("<td>" + g.getHomeTeam().getName() + "</td>");
                out.println("<td class='text-center fw-bold bg-light'>" + g.getHomeScore() + " - " + g.getAwayScore() + "</td>");
                out.println("<td class='text-end'>" + g.getAwayTeam().getName() + "</td>");
                out.println("</tr>");
            }
        }
        out.println("</tbody></table>");
        out.println("</div></div>");
    }

    private void insertInfo() {
        try {

            // Leagues
            ejbRequest.createLeague(new LeagueDetails("L1", "Mountain", "Soccer"));
            ejbRequest.createLeague(new LeagueDetails("L2", "Valley", "Basketball"));
            ejbRequest.createLeague(new LeagueDetails("L3", "Foothills", "Soccer"));

            // Teams
            ejbRequest.createTeamInLeague(new TeamDetails("T1", "Honey Bees", "Visalia"), "L1");
            ejbRequest.createTeamInLeague(new TeamDetails("T2", "Gophers", "Manteca"), "L1");
            ejbRequest.createTeamInLeague(new TeamDetails("T5", "Crows", "Orland"), "L1");

            ejbRequest.createTeamInLeague(new TeamDetails("T3", "Deer", "Bodie"), "L2");
            ejbRequest.createTeamInLeague(new TeamDetails("T4", "Trout", "Truckee"), "L2");

            // Players
            ejbRequest.createPlayer("P1", "Phil Jones", "goalkeeper", 100.00);
            ejbRequest.addPlayer("P1", "T1");
            ejbRequest.createPlayer("P6", "Ian Carlyle", "goalkeeper", 555.00);
            ejbRequest.addPlayer("P6", "T2");

            // T1 vs T2 (2-1) -> T1 castiga 3p
            ejbRequest.createGame("L1", "T1", "T2", 2, 1);

            // T5 vs T1 (0-3) -> T1 castiga 3p
            ejbRequest.createGame("L1", "T5", "T1", 0, 3);

            // T2 vs T5 (1-1) -> Egal 1p fiecare
            ejbRequest.createGame("L1", "T2", "T5", 1, 1);

            // T1 vs T5 (0-0) -> Egal 1p
            ejbRequest.createGame("L1", "T1", "T5", 0, 0);

            // Rezultat asteptat pentru T1: 3 + 3 + 1 = 7 puncte

            // 3. POPULAREA LIGII 2 (L2) - Basketball
            // T3 vs T4 (80-75) -> T3 castiga 3p
            ejbRequest.createGame("L2", "T3", "T4", 80, 75);

            // T4 vs T3 (88-85) -> T4 castiga 3p
            ejbRequest.createGame("L2", "T4", "T3", 88, 85);

            // T3 vs T4 (92-92) -> Egal 1p fiecare
            ejbRequest.createGame("L2", "T3", "T4", 92, 92);

            // T4 vs T3 (78-81) -> T3 castiga 3p
            ejbRequest.createGame("L2", "T4", "T3", 78, 81);

            // Rezultat asteptat: T3 = 3 + 3 + 1 = 7 puncte, T4 = 3 + 1 = 4 puncte

        } catch (Exception ex) {
            // Prindem exceptia si o afisam in consola serverului
            ex.printStackTrace();
        }
    }
}