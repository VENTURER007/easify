/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

// const {onRequest} = require("firebase-functions/v2/https");
// const logger = require("firebase-functions/logger");

// Create and deploy your first functions
// https://firebase.google.com/docs/functions/get-started

// exports.helloWorld = onRequest((request, response) => {
//   logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });
const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp();
exports.findNearestServicePartner = functions.database.ref("/orders/{orderId}")
    .onCreate(async (snapshot, context) => {
      const order = snapshot.val();
      const orderId = context.params.orderId;

      const orderLocation = order.location;

      // Fetch all service partners
      const servicePartnersSnapshot = await admin.database()
          .ref("/service_partners").once("value");
      const servicePartners = servicePartnersSnapshot.val();

      let nearestServicePartner = null;
      let minDistance = Number.MAX_VALUE;

      // Calculate distances and find the nearest service partner
      for (const servicePartnerId in servicePartners) {
        if (Object.prototype.hasOwnProperty
            .call(servicePartners, servicePartnerId)) {
          const servicePartner = servicePartners[servicePartnerId];
          const servicePartnerLocation = servicePartner.location;

          // Calculate distance using Haversine formula
          const distance = calculateDistance(
            orderLocation.latitude, orderLocation.longitude,
            servicePartnerLocation.latitude, servicePartnerLocation.longitude,
          );

          if (distance < minDistance) {
          minDistance = distance;
          nearestServicePartner = servicePartner;
          }
        }
      }

      if (nearestServicePartner) {
      // Assign the nearest service partner to the order
        await admin.database()
            .ref(`/orders/${orderId}/service_partner_id`)
            .set(nearestServicePartner);
      }

      // Function completed successfully
      return null;
    });
/**
 * Calculate the distance between two sets of latitude and longitude coordinates using the Haversine formula.
 * @param {number} lat1 - Latitude of the first location.
 * @param {number} lon1 - Longitude of the first location.
 * @param {number} lat2 - Latitude of the second location.
 * @param {number} lon2 - Longitude of the second location.
 * @returns {number} - The calculated distance.
 */
function calculateDistance(lat1, lon1, lat2, lon2) {
  const R = 6371; // Radius of the Earth in kilometers
  const dLat = degToRad(lat2 - lat1);
  const dLon = degToRad(lon2 - lon1);

  const a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
  Math.cos(degToRad(lat1)) * Math.cos(degToRad(lat2)) *
  Math.sin(dLon / 2) * Math.sin(dLon / 2);

  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  const distance = R * c;

  return distance;
}
/**
 * Convert degrees to radians.
 * @param {number} deg - The value in degrees.
 * @returns {number} - The converted value in radians.
 */
function degToRad(deg) {
  return deg * (Math.PI / 180);
}

